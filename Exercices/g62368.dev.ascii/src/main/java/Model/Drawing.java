package Model;

import java.util.ArrayList;
import java.util.List;

public class Drawing {
     int width;
     int height;
     List<Shape> shapes;


    public Drawing(int width, int height) {
        this.width = width;
        this.height = height;
        this.shapes = new ArrayList<>();
    }


    public void add(Shape shape) {

        shapes.add(shape);
    }


    protected boolean remove(Shape shape) {
        return shapes.remove(shape);
    }


    public boolean remove(int index) {
        if (index >= 0 && index < shapes.size()) {
            shapes.remove(index);
            return true;
        }
        return false;
    }


    protected Shape getShapeAt(Point point) {
        for (Shape shape : shapes) {
            if (shape.isInside(point)) {
                return shape;
            }
        }
        return null;
    }


    protected Shape getShapeAt(int index) {
        if (index >= 0 && index < shapes.size()) {
            return shapes.get(index);
        }
        return null;
    }


    public int getWidth() {
        return width;
    }


    public int getHeight() {
        return height;
    }


    public void displayShapes() {
        for (Shape shape : shapes) {
            System.out.println(shape);
        }
    }
    public char getColorAt(int x, int y) {
        Point point = new Point(x, y);

        for (int i = shapes.size() - 1; i >= 0; i--) {
            Shape shape = shapes.get(i);
            if (shape.isInside(point)) {
                return shape.getColor();
            }
        }
        return ' ';
    }
}
