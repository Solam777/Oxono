package g62368_oxono.project.model;

public class Pawn extends Piece{
    private Color color;

    public Pawn(Mark mark, Color color) {
        super(mark);
        this.color = color;
    }

    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return "Pawn{" +
                "color=" + color +
                ", mark=" + getMark() +
                '}';
    }
}
