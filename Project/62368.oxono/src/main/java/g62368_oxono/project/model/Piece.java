package g62368_oxono.project.model;

/**
 * The Piece class represents an abstract game piece.
 */
public abstract class Piece {
    /**
     * The mark of the piece.
     */
    private Mark mark;

    /**
     * Constructs a Piece with the specified mark.
     *
     * @param mark the mark of the piece
     */
    public Piece(Mark mark) {
        this.mark = mark;
    }

    /**
     * Returns the mark of the piece.
     *
     * @return the mark of the piece
     */
    public Mark getMark() {
        return mark;
    }
}