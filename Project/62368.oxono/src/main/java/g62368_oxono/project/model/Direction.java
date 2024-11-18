package g62368_oxono.project.model;

public enum Direction {
    UP(-1,0),
    DOWN(1,0),
    LEFT(0,-1),
    RIGHT(0,1);

    private int X;
    private int Y;

    Direction(int x, int y) {
        X = x;
        Y = y;
    }

    public int getX() {
        return X;
    }

    public int getY() {
        return Y;
    }
}
