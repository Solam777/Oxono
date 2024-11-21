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
    private Mark lastMoveMark;
    private int size;
    private Position totemXPosition;
    private Position totemoPosition;
    private CommandManager commandManager;
    private List<Observer> observers;

    public Game() {
        this.player1 = new Player(Color.BLACK);
        this.player2 = new Player(Color.PINK);
        currentPlayer = player1;
        this.observers = new ArrayList<>();
    }

    public void initializeGame(int size){
        this.board = new Board(size);
        this.size = size;
        totemXPosition = board.getTotemPosition(Mark.X);
        totemoPosition = board.getTotemPosition(Mark.O);

    }
    public Player getCurrentPlayer(){
        return currentPlayer;
    }

   public void playPawn(Position position, Pawn pawn){
       board.placePawn(position, pawn);
       Position totemPosition = board.getTotemPosition(board.getLastMoveTotem().getMark());
       Command command = new InsertPawn(position, board, totemPosition , player1,pawn);
       commandManager.executeCommand(command);
   }

   public void playTotem(Position position, Totem totem){
        Position lastMoveTotem = board.getTotemPosition(board.getLastMoveTotem().getMark());
        board.placeTotem(position, totem);
        Command command = new MoveTotem(lastMoveTotem,position,totem,board);
        commandManager.executeCommand(command);

   }

    public boolean iswon(){
        if (hasPlayerForfeited()) {
            draw();
            return false;
        }
        Pawn pawn = board.getRecentPawnPlace();
        if (pawn == null){
            return false;
        }
        return board.Iswin(board.getTotemPosition(pawn.getMark()), pawn);
    }

    /*
    * on met les pieces du joeurs a 0*/
    public void draw(){
        currentPlayer.setdraw();
    }

    /*
    * si le joeur decide d'abandonner*/
    private boolean hasPlayerForfeited() {
        return true;
    }

    public void switchPlayer(){
        currentPlayer = (currentPlayer == player1)? player2 : player1;
    }
    public void undo(){
        commandManager.undo();
    }

    public void redo(){
        commandManager.redo();
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
            observer.update(this,event);
        }
    }


}

