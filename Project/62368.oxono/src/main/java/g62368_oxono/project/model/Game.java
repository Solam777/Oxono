
package g62368_oxono.project.model;

import g62368_oxono.project.model.Commande.Command;
import g62368_oxono.project.model.Commande.CommandManager;
import g62368_oxono.project.model.Commande.InsertPawn;
import g62368_oxono.project.model.Commande.MoveTotem;
import g62368_oxono.project.model.Observer.Event;
import g62368_oxono.project.model.Observer.Observable;
import g62368_oxono.project.model.Observer.Observer;

import java.util.ArrayList;
import java.util.List;

public class Game implements Observable {
    private Board board;
    private Player player1;
    private Player player2;
    private Player currentPlayer;
    private Totem lastMoveTotem;
    private int size;
    private Position totemXPosition;
    private Position totemoPosition;
    private CommandManager commandManager;
    private List<Observer> observers;
    private Position lastMovePawn;
    private boolean draw;
    private boolean gameOver;

    public Game() {
        this.player1 = new Player(Color.BLACK);
        this.player2 = new Player(Color.PINK);
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
       lastMoveTotem = null;
    }

    public Player getCurrentPlayer(){
        return currentPlayer;
    }

    public void playPawn(Position position, Pawn pawn){
        board.placePawn(position, pawn);
        Totem lastMoveTotem = board.findTheTotem( pawn.getMark());
        Position totemPosition = board.getTotemPosition(lastMoveTotem.getMark());
        lastMovePawn = position;
        Command command = new InsertPawn(position, board, totemPosition , getCurrentPlayer() ,pawn);
        commandManager.executeCommand(command);
    }


    public Board getBoard() {
        return board;
    }

    public void playTotem(Position position, Totem totem){
        Position lastPosTotem = getTotemPositionForMove(totem.getMark());
            board.placeTotem(position, totem);
            updateTotemPosition(totem,position);
            lastMoveTotem = totem;
            Command command = new MoveTotem(lastPosTotem, position, totem, board);
            commandManager.executeCommand(command);
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
            return board.Iswin(lastMovePawn, (Pawn) token);
        }
        return false;
    }

    public boolean isDraw() {
        if (!checkWin()) {
            return (player1.hasPawn(Mark.X) && player1.hasPawn(Mark.O)) ||
                    player2.hasPawn(Mark.X) && player2.hasPawn(Mark.O);
        }
        return false;
    }

    public int[] getRemainingPawns(){
        int pawnsXplayerPink = player1.getRemainingPawns(Mark.X);
        int pawns0playerPink = player1.getRemainingPawns(Mark.O);
        int pawnsXplayerBlack = player2.getRemainingPawns(Mark.X);
        int pawns0playerBlack = player2.getRemainingPawns(Mark.O);
        return new int[]{pawnsXplayerPink, pawns0playerPink, pawnsXplayerBlack, pawns0playerBlack};
    }



    public void switchPlayer(){
        currentPlayer = (currentPlayer == player1)? player2 : player1;
    }

    public void undo(){
        commandManager.undo();
    }
    public boolean canUndo(){
        return!commandManager.getUndoStack().isEmpty();
    }

    public void redo(){
        commandManager.redo();
    }
    public boolean canRedo(){
        return!commandManager.getRedoStack().isEmpty();
    }
    @Override
    public void addObserver(Observer observer) {
    }

    @Override
    public void removeObserver(Observer observer) {
    }

    @Override
    public void notifyObservers(Event event) {
        for (Observer observer : observers) {
            observer.update(this, event);
        }
    }

    public Player getPlayer(Mark mark) {
        if (mark == Mark.X) {
            return player1;
        } else if (mark == Mark.O) {
            return player2;
        } else {
            throw new IllegalArgumentException("Invalid mark: " + mark);
        }
    }
    public void restart() {
        // Reset the board
        board = new Board(size);

        // Reset the players' pawns and racks
        player1.reset();
        player2.reset();

        // Reset the current player
        currentPlayer = player1;

        // Reset the game state
        gameOver = false;
        draw = false;

        // Clear the command history
        commandManager.clearHistory();
    }

    public int getSize() {
        return size;
    }

    public Piece getPiece(Position position) {
      return getBoard().getPiece(position);
    }
    public boolean isTotem(Piece piece) {
        return piece instanceof Totem;
    }


}