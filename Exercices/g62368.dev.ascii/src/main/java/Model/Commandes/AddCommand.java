package Model.Commandes;

import Model.Drawing;
import Model.Shape;

public class AddCommand implements Command {
    private Drawing drawing;
    private Shape shape;

    public AddCommand(Drawing drawing, Shape shape) {
        this.drawing = drawing;
        this.shape = shape;
    }

    @Override
    public void execute(){
        drawing.add(shape);
    }

    @Override
    public void unexecute() {
        drawing.remove(shape);
    }

}
