package Model;


import java.util.Scanner;


public class Point {
    double x;
    double y;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public Point move(double dY, double dX){
        x += dX;
        y += dY;


        return this;

    }
    public double  DistanceTo(Point other){
         double x = this.x - other.getX();
         double y = this.y - other.getY();
         return Math.sqrt(x*x + y*y);

    }
}
