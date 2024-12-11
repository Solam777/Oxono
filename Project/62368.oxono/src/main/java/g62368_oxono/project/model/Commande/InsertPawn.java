package g62368_oxono.project.model.Commande;

import g62368_oxono.project.model.Board;
import g62368_oxono.project.model.Pawn;
import g62368_oxono.project.model.Player;
import g62368_oxono.project.model.Position;

public class InsertPawn implements Command  {
    private Position pawnPosition;
    private Board board;
    private Position totemPosition;
    private Player player;
    private Pawn pawn;


    public InsertPawn(Position pawnPosition, Board board, Position totemPosition, Player player, Pawn pawn) {
        this.pawnPosition = pawnPosition;
        this.board = board;
        this.totemPosition = totemPosition;
        this.player = player;
        this.pawn = pawn;
    }

    @Override
    public void execute() {
        if (player.hasPawn(pawn.getMark())){
            board.placePawn(pawnPosition,pawn);

        }
    }

    @Override
    public void unexecute() {
        player.addPawn(pawn);
        board.removePawn(pawnPosition);

    }
}
