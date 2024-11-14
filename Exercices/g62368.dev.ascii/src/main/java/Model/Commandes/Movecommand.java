package Model.Commandes;

import Model.Drawing;
import Model.Shape;

public class Movecommand  implements Command{
    private Shape shape;
    private double x;
    private double y;
    private double x_base;
    private double y_base;

    public Movecommand(Shape shape, double x, double y) {
        this.shape = shape;
        this.x = x;
        this.y = y;
    }

    @Override
    public void execute() {
        x_base = x;
        y_base = y;
        shape.move(x,y);
    }

    @Override
    public void unexecute() {
        shape.move(x_base,y_base);

    }
}
