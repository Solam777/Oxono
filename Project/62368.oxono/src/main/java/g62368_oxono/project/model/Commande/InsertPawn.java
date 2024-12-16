package g62368_oxono.project.model.Commande;

import g62368_oxono.project.model.Board;
import g62368_oxono.project.model.Pawn;
import g62368_oxono.project.model.Player;
import g62368_oxono.project.model.Position;

public class InsertPawn implements Command  {
    private final Position pawnPosition;
    private final Board board;
    private final Position totemPosition;
    private final Player player;
    private final Pawn pawn;


    /**
     * Constructs an InsertPawn command with the specified parameters.
     *
     * @param pawnPosition the position where the pawn will be placed
     * @param board the board on which the pawn will be placed
     * @param totemPosition the position of the totem
     * @param player the player who is placing the pawn
     * @param pawn the pawn to be placed
     */
    public InsertPawn(Position pawnPosition, Board board, Position totemPosition, Player player, Pawn pawn) {
        this.pawnPosition = pawnPosition;
        this.board = board;
        this.totemPosition = totemPosition;
        this.player = player;
        this.pawn = pawn;
    }

    /**
     * Executes the command by placing the pawn at the specified position on the board.
     */
    @Override
    public void execute() {
        board.setPawn(pawnPosition,pawn);
    }

    /**
     * Undoes the command by removing the pawn from the board and returning it to the player.
     */
    @Override
    public void unexecute() {
        player.addPawn(pawn);
        board.removePawn(pawnPosition);
    }
}
