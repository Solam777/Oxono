package g62368_oxono.project.model.Commande;

import g62368_oxono.project.model.Board;
import g62368_oxono.project.model.Position;
import g62368_oxono.project.model.Totem;

public class MoveTotem implements Command {
    private Position from; // the position where the totem is currently located.
    private Position to; // the position where the totem will be moved to.
    private Totem totem;
    private Board board;

    public MoveTotem(Position from, Position to, Totem totem, Board board) {
        this.from = from;
        this.to = to;
        this.totem = totem;
        this.board = board;
    }

    /**
     * Executes the command by moving the totem to the target position.
     */
    @Override
    public void execute() {
        board.setTotem(to, totem);
    }

    /**
     * Undoes the command by moving the totem back to its initial position.
     */
    @Override
    public void unexecute() {
        board.setTotem(from, totem);
    }
}
