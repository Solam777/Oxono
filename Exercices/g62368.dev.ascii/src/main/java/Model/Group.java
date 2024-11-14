package Model;

import java.util.ArrayList;
import java.util.List;

public class Group extends ColoredShape {
    private List<Shape> shapes;

    public Group(char color) {
        super(color);
        this.shapes =new ArrayList<>();
    }

    public boolean isinde(Point p){
        for (Shape shape : shapes){
            if (shape.isInside(p)){
                return true;
            }
        }
        return false;
    }

    public void move(double dx , double dy){
        for (Shape shape : shapes){
            shape.move(dx,dy);
        }
    }

    public void addshape(Shape shape){
        shapes.add(shape);
    }

    public List<Shape> getShapes(){
        return shapes;
    }


}
