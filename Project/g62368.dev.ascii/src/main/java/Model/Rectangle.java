package Model;

public class Rectangle extends  ColoredShape {
    private double width;
    private double height;
    private Point upperLeft;


    public double getWidth() {
        return width;
    }

    public double getHeight() {
        return height;
    }

    public char getColor() {
        return color;
    }

    public Point getUpperLeft() {
        return upperLeft;
    }

    public Rectangle(char color, double width, double height, Point upperLeft) {
        super(color);
        this.width = width;
        this.height = height;
        this.upperLeft = upperLeft;

    }

    public boolean isInside(Point p) {
        double x = p.getX();
        double y = p.getY();
        return (x >= upperLeft.getX() && x <= upperLeft.getX() + width) &&
                (y <= upperLeft.getY() && y >= upperLeft.getY() - height);
    }

    public void move(double dx, double dy) {
        upperLeft.x += dx;
        upperLeft.y += dy;
    }



}