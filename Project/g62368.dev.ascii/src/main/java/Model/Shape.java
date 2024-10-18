package Model;

public interface Shape   {

    boolean isInside(Point p);

    void move(double dx, double dy);

    void setColor(char color);

    char getColor();
}

