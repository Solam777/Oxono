package g62368_oxono.project.model;

/**
 * The Pawn class represents a pawn piece in the game.
 */
public class Pawn extends Piece {
    /**
     * The color of the pawn.
     */
    private Color color;

    /**
     * Constructs a Pawn with the specified mark and color.
     *
     * @param mark the mark of the pawn
     * @param color the color of the pawn
     */
    public Pawn(Mark mark, Color color) {
        super(mark);
        this.color = color;
    }

    /**
     * Returns the color of the pawn.
     *
     * @return the color of the pawn
     */
    public Color getColor() {
        return color;
    }

    /**
     * Returns a string representation of the pawn.
     *
     * @return a string representation of the pawn
     */
    @Override
    public String toString() {
        return
                "color=" + color +
                ", mark=" + getMark();
    }
}