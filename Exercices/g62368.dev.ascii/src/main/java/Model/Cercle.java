package Model;

public class Cercle extends ColoredShape{
    Double radius;
    Point center;

    public Cercle(char color, Double radius, Point center) {
        super(color);
        this.radius = radius;
        this.center = center;
    }

    public boolean isInside(Point p) {
        double distance = Math.sqrt(Math.pow(p.getX() - center.getX(), 2) + Math.pow(p.getY() - center.getY(), 2));
        return distance <= radius;
    }

    public void move(double dx,double dy){
        center.y =+ dy;
        center.x =+ dx;
    }
}
