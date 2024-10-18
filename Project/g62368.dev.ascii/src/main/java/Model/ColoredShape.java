package Model;

public class ColoredShape implements Shape {
    char color;

    public ColoredShape(char color) {
        this.color = color;
    }

    public char getColor() {
        return color;
    }

    @Override
    public boolean isInside(Point p) {
        return false;
    }

    @Override
    public void move(double dx, double dy) {

    }

    public void setColor(char color) {
        this.color = color;
    }

}
