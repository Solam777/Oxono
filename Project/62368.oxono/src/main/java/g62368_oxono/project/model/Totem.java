package g62368_oxono.project.model;

/**
 * The Totem class represents a specific type of Piece in the game.
 * It extends the Piece class and is initialized with a Mark.
 */
public class Totem extends Piece {

    /**
     * Constructs a Totem with the specified mark.
     *
     * @param mark the mark to be assigned to this Totem
     */
    public Totem(Mark mark) {
        super(mark);
    }

    /**
     * Returns a string representation of the Totem.
     *
     * @return a string containing the mark of the Totem
     */
    @Override
    public String toString() {
        return " " + getMark();
    }
}