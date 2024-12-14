
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

    public Game() {
        this.player1 = new Player(Color.PINK);
        this.player2 = new Player(Color.BLACK);
        currentPlayer = player1;
        this.observers = new ArrayList<>();
        commandManager = new CommandManager();
    }

    public void initializeGame(int size){
        this.board = new Board(size);
        this.board.initialize();
        this.size = size;
        totemXPosition = board.getTotemPosition(Mark.X);
        totemoPosition = board.getTotemPosition(Mark.O);
        notifyObservers(ObservableEvent.GAME_START);
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void playPawn(Position position){
        if (!victory){
            if(canPlacePawn){
                if (lastMoveTotem == null) {
                    System.out.println("No Totem has been moved yet.");
                    return;
                }
                Pawn pawn = new Pawn(lastMoveTotem.getMark(),currentPlayer.getColor());
                Position totemPosition = board.getTotemPosition(lastMoveTotem.getMark());
                board.placePawn(position, pawn);
                currentPlayer.removePawn(pawn);
                Command command = new InsertPawn(position, board, totemPosition , getCurrentPlayer() ,pawn);
                commandManager.executeCommand(command);
                notifyObservers(ObservableEvent.PLACE_PAWN);
                if (board.isWin(position,pawn)){
                    victory = true;
                    notifyObservers(ObservableEvent.WIN);
                } else {
                    switchPlayer();
                }
                canPlacePawn = false;

            }
            else {
                System.out.println("Impossible jouer un pawn");
            }
        }
    }

    public void playTotem(Position position, Totem totem){
       if (!victory){
           if(!canPlacePawn){
               Position lastPosTotem = getTotemPositionForMove(totem.getMark());
               updateTotemPosition(totem,position);
               Command command = new MoveTotem(lastPosTotem, position, totem, board);
               commandManager.executeCommand(command);
               notifyObservers(ObservableEvent.MOVE_TOTEM);
               canPlacePawn = true;
               lastMoveTotem = totem;
           }
           else {
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


    public boolean checkWin() {
        Piece token = board.getPiece(lastMovePawn);
        if (!board.isTotem(token)) {
            return board.isWin(lastMovePawn, (Pawn) token);
        }
        return false;
    }

    public boolean  isDraw() {
       return (getCurrentPlayer().getPawnsO().isEmpty() && getCurrentPlayer().getPawnsX().isEmpty());
    }

    public int[] getRemainingPawns(){
        int pawnsXplayerPink = player1.getRemainingPawns(Mark.X);
        int pawns0playerPink = player1.getRemainingPawns(Mark.O);
        int pawnsXplayerBlack = player2.getRemainingPawns(Mark.X);
        int pawns0playerBlack = player2.getRemainingPawns(Mark.O);
        System.out.println( pawnsXplayerPink+ " "+  pawns0playerBlack + " "+ pawnsXplayerBlack + " "+ pawns0playerBlack);
        return new int[]{pawnsXplayerPink, pawns0playerPink, pawnsXplayerBlack, pawns0playerBlack};
    }

    public void switchPlayer(){
        currentPlayer = (currentPlayer == player1)? player2 : player1;
    }


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
            if (victory){
                victory = false;

            }
            notifyObservers(ObservableEvent.UNDO);
        } else {
            System.out.println("No commands to undo.");
        }
    }

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

    public boolean canUndo(){
        return!commandManager.getUndoStack().isEmpty();
    }

    public boolean canRedo(){
        return!commandManager.getRedoStack().isEmpty();
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

    @Override
    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers(ObservableEvent event) {
        for (Observer observer : observers) {
            observer.update(event);
        }
    }

    public int getSize() {
        return size;
    }

    public Piece getPiece(Position position) {
      return board.getPiece(position);
    }

    public boolean isTotem(Piece piece) {
        return piece instanceof Totem;
    }

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

  public void quitGame() {
      System.exit(0);
        notifyObservers(ObservableEvent.QUIT);
  }

  public List<Position> getFreeposTotem(Totem totem) {
       return board.getValidMovesTotem(totem);
  }

  public List<Position> getFreeposPawn(Totem totem) {
      return board.getValidMovesPawn(totem);
  }

}