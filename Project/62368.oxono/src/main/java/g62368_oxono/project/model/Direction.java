package g62368_oxono.project.model;

/**
 * The Direction enum represents the possible directions for movement.
 */
public enum Direction {
    /**
     * Represents the upward direction.
     */
    UP(-1,0),

    /**
     * Represents the downward direction.
     */
    DOWN(1,0),

    /**
     * Represents the leftward direction.
     */
    LEFT(0,-1),

    /**
     * Represents the rightward direction.
     */
    RIGHT(0,1);

    /**
     * The X coordinate change for the direction.
     */
    private int X;

    /**
     * The Y coordinate change for the direction.
     */
    private int Y;

    /**
     * Constructs a Direction with the specified X and Y coordinate changes.
     *
     * @param x the X coordinate change
     * @param y the Y coordinate change
     */
    Direction(int x, int y) {
        X = x;
        Y = y;
    }

    /**
     * Returns the X coordinate change for the direction.
     *
     * @return the X coordinate change
     */
    public int getX() {
        return X;
    }

    /**
     * Returns the Y coordinate change for the direction.
     *
     * @return the Y coordinate change
     */
    public int getY() {
        return Y;
    }
}