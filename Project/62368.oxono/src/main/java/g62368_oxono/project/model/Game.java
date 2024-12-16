
package g62368_oxono.project.model;

import g62368_oxono.project.model.Commande.Command;
import g62368_oxono.project.model.Commande.CommandManager;
import g62368_oxono.project.model.Commande.InsertPawn;
import g62368_oxono.project.model.Commande.MoveTotem;
import g62368_oxono.project.model.Observer.Observable;
import g62368_oxono.project.model.Observer.ObservableEvent;
import g62368_oxono.project.model.Observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Game implements Observable {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private int size;
    private Position totemXPosition;
    private Position totemoPosition;
    private CommandManager commandManager;
    private List<Observer> observers;
    private Position lastMovePawn;
    public Totem lastMoveTotem;
    private boolean canPlacePawn = false;
    private boolean victory;


    /**
     * Constructs a new {@code Game} object and initializes players and observers.
     */
    public Game() {
        this.player1 = new Player(Color.PINK);
        this.player2 = new Player(Color.BLACK);
        currentPlayer = player1;
        this.observers = new ArrayList<>();
        commandManager = new CommandManager();
    }
    /**
     * Displays a help menu with a list of available commands.
     */
    /**
     * Initializes the game with a specified board size and resets all components.
     *
     * @param size the size of the game board
     */
    public void initializeGame(int size) {
        this.board = new Board(size);
        this.board.initialize();
        this.size = size;
        totemXPosition = board.getTotemPosition(Mark.X);
        totemoPosition = board.getTotemPosition(Mark.O);
        notifyObservers(ObservableEvent.GAME_START);
    }

    /**
     * Returns the current player.
     *
     * @return the current player
     */
    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    /**
     * Plays a Pawn at the specified position, checking for win conditions and switching turns.
     *
     * @param position the position to place the Pawn
     */
    public void playPawn(Position position) {
        if (!victory && !egality() && !isDraw()) {
            if (canPlacePawn) {
                if (lastMoveTotem == null) {
                    System.out.println("No Totem has been moved yet.");
                    return;
                }
                Pawn pawn = new Pawn(lastMoveTotem.getMark(), currentPlayer.getColor());
                Position totemPosition = board.getTotemPosition(lastMoveTotem.getMark());
                board.placePawn(position, pawn);
                currentPlayer.removePawn(pawn);
                Command command = new InsertPawn(position, board, totemPosition, getCurrentPlayer(), pawn);
                commandManager.executeCommand(command);
                notifyObservers(ObservableEvent.PLACE_PAWN);
                if (board.isWin(position, pawn)) {
                    victory = true;
                    notifyObservers(ObservableEvent.WIN);
                }
                else {
                    switchPlayer();
                }
                canPlacePawn = false;
            } else {
                System.out.println("Impossible jouer un pawn");
            }
        }
    }


    /**
     * Moves a Totem to the specified position.
     *
     * @param position the new position for the Totem
     * @param totem    the Totem to move
     */
    public void playTotem(Position position, Totem totem) {
        if (!victory && !egality() && !isDraw() ) {
            if (!canPlacePawn) {
                Position lastPosTotem = getTotemPositionForMove(totem.getMark());
                updateTotemPosition(totem, position);
                Command command = new MoveTotem(lastPosTotem, position, totem, board);
                commandManager.executeCommand(command);
                notifyObservers(ObservableEvent.MOVE_TOTEM);
                canPlacePawn = true;
                lastMoveTotem = totem;
            } else {
                System.out.println("Impossible de placer un totem");
            }
        }
    }

    private Position getTotemPositionForMove(Mark mark) {
        return mark == Mark.X ? totemXPosition : totemoPosition;
    }

    private void updateTotemPosition(Totem totem, Position newPos) {
        if (totem.getMark() == Mark.X) {
            totemXPosition = newPos;
        } else {
            totemoPosition = newPos;
        }
    }

    /**
     * Checks if the current game state is a winning state.
     *
     * @return true if the game is won, false otherwise
     */
    public boolean checkWin() {
        Piece token = board.getPiece(lastMovePawn);
        if (!board.isTotem(token)) {
            return board.isWin(lastMovePawn, (Pawn) token);
        }
        return false;
    }

    /**
     * Checks if the current game state is a draw.
     *
     * @return true if the game is a draw, false otherwise
     */
    public boolean isDraw() {
        return (getCurrentPlayer().getPawnsO().isEmpty() && getCurrentPlayer().getPawnsX().isEmpty());
    }

    /**
     * Retrieves the remaining Pawns for both players.
     *
     * @return an array containing the counts of Pawns for both players
     */
    public int[] getRemainingPawns() {
        int pawnsXplayerPink = player1.getRemainingPawns(Mark.X);
        int pawns0playerPink = player1.getRemainingPawns(Mark.O);
        int pawnsXplayerBlack = player2.getRemainingPawns(Mark.X);
        int pawns0playerBlack = player2.getRemainingPawns(Mark.O);
        System.out.println(pawnsXplayerPink + " " + pawns0playerBlack + " " + pawnsXplayerBlack + " " + pawns0playerBlack);
        return new int[]{pawnsXplayerPink, pawns0playerPink, pawnsXplayerBlack, pawns0playerBlack};
    }

    /**
     * Switches the turn to the other player.
     */
    public void switchPlayer() {
        currentPlayer = (currentPlayer == player1) ? player2 : player1;
    }


    /**
     * Undoes the last command if possible and updates the game state.
     */
    public void undo() {
        if (canUndo()) {
            Command command = commandManager.getUndoStack().peek();
            boolean isPawnMove = command instanceof InsertPawn;
            System.out.println("Undoing command: " + command.getClass().getSimpleName());
            commandManager.undo();
            updateGameStateAfterUndoRedo();

            // Ne pas changer de joueur si c'est un placement de pion
            if (isPawnMove) {
                switchPlayer();
                System.out.println("Switching player after undo totem move");
            } else {
                System.out.println("Keeping same player after undo pawn move");
            }
            if (victory) {
                victory = false;

            }
            notifyObservers(ObservableEvent.UNDO);
        } else {
            System.out.println("No commands to undo.");
        }
    }


    /**
     * Redoes the last undone command if possible and updates the game state.
     */
    public void redo() {
        if (canRedo()) {
            Command command = commandManager.getRedoStack().peek();
            boolean isPawnMove = command instanceof InsertPawn;
            System.out.println("Redoing command: " + command.getClass().getSimpleName());
            commandManager.redo();
            updateGameStateAfterUndoRedo();

            // Ne pas changer de joueur si c'est un placement de pion
            if (!isPawnMove) {
                switchPlayer();
                System.out.println("Switching player after redo totem move");
            } else {
                System.out.println("Keeping same player after redo pawn move");
            }

            notifyObservers(ObservableEvent.REDO);
        } else {
            System.out.println("No commands to redo.");
        }
    }

    /**
     * Checks if undo is possible.
     *
     * @return true if there are commands to undo, false otherwise
     */
    public boolean canUndo() {
        return !commandManager.getUndoStack().isEmpty();
    }

    /**
     * Checks if redo is possible.
     *
     * @return true if there are commands to redo, false otherwise
     */
    public boolean canRedo() {
        return !commandManager.getRedoStack().isEmpty();
    }

    private void updateGameStateAfterUndoRedo() {
        // On regarde quelle commande on vient d'annuler depuis l'UndoStack
        Command commandToUndo = null;
        if (!commandManager.getUndoStack().isEmpty()) {
            commandToUndo = commandManager.getUndoStack().peek();
        }

        if (commandToUndo instanceof InsertPawn) {
            canPlacePawn = false;
        } else if (commandToUndo instanceof MoveTotem) {
            canPlacePawn = true;

        } else if (commandManager.getUndoStack().isEmpty()) {
            currentPlayer = player1;
            canPlacePawn = false;
            lastMoveTotem = null;

        }
    }

    /**
     * Adds an observer to the game.
     *
     * @param observer the observer to add
     */
    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    /**
     * Removes an observer from the game.
     *
     * @param observer the observer to remove
     */
    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    /**
     * Notifies all observers of a specific event.
     *
     * @param event the event to notify observers about
     */
    @Override
    public void notifyObservers(ObservableEvent event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }

    /**
     * Retrieves the size of the game board.
     *
     * @return the size of the game board
     */
    public int getSize() {
        return size;
    }

    /**
     * Retrieves the piece at the specified position on the board.
     *
     * @param position the position to query
     * @return the {@link Piece} at the specified position
     */

    public Piece getPiece(Position position) {
        return board.getPiece(position);
    }

    /**
     * Checks if a given piece is a Totem.
     *
     * @param piece the piece to check
     * @return true if the piece is a Totem, false otherwise
     */
    public boolean isTotem(Piece piece) {
        return piece instanceof Totem;
    }

    /**
     * Checks if moving a Totem to a position is valid.
     *
     * @param clickTotemPos the position to move the Totem to
     * @param totem         the Totem to move
     * @return true if the move is valid, false otherwise
     */
    public boolean isValidMove(Position clickTotemPos, Totem totem) {
        try {
            // Tente de valider le mouvement
            board.validateMoveTotem(clickTotemPos, totem);
            return true; // Si aucune exception n'est levée, le mouvement est valide
        } catch (OxonoExecption e) {
            // Capture et gère l'erreur, retourne false si le mouvement est invalide
            System.out.println("Mouvement invalide : " + e.getMessage());
            return false;
        }
    }

    /**
     * Quits the game and notifies observers of the quit event.
     */
    public void quitGame() {
        System.exit(0);
        notifyObservers(ObservableEvent.QUIT);
    }

    /**
     * Retrieves a list of free positions for moving a Totem.
     *
     * @param totem the Totem to move
     * @return a list of valid positions for the Totem
     */
    public List<Position> getFreeposTotem(Totem totem) {
        return board.getValidMovesTotem(totem);
    }

    /**
     * Retrieves a list of free positions for placing a Pawn.
     *
     * @param totem the reference Totem for placement
     * @return a list of valid positions for placing a Pawn
     */
    public List<Position> getFreeposPawn(Totem totem) {
        return board.getValidMovesPawn(totem);
    }


    /**
 * Checks if the game is in a draw state.
 *
 * @return true if the game is a draw, false otherwise
 */
public boolean egality() {
    if (player1.getPawnsX().isEmpty() && player1.getPawnsO().isEmpty() && player2.getPawnsX().isEmpty() && player2.getPawnsO().isEmpty()) {
        notifyObservers(ObservableEvent.DRAW);
        return true;
    } else return false;
}


}