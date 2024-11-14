package Model.Commandes;

import Model.ColoredShape;
import Model.Drawing;
import Model.Shape;

public class ChangeColorCommand implements  Command {
    Drawing drawing;
    Shape shape;
    char coloredShape;
    char origincolor;

    public ChangeColorCommand(Shape shape, char coloredShape) {
        this.shape = shape;
        this.coloredShape = coloredShape;
    }

    public void execute(){
        origincolor = coloredShape;
        shape.setColor(coloredShape);

    }
    public void unexecute(){
        shape.setColor(origincolor);
    }
}
