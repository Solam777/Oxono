package g62368_oxono.project.model;

public class Game {
    private Board board;
    private Player currentPlayer;
    private Mark lastMoveMark;
    private int size;

    public Game(Board board, int size) {
        this.board = new Board(size);
        this.currentPlayer = new Player(Color.PINK);
        this.size = size;
    }
    public void playTotem(Position position,Mark mark){
        Totem totem = board.theTotem(mark);
        board.placeTotem(position,totem);
    }
    public void playPawn(Position position,Mark mark){
        board.placePawn(position,new Pawn(mark,currentPlayer.getColor()));
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
}

