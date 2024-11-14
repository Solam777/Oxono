package Model.Commandes;

import Model.ColoredShape;
import Model.Drawing;
import Model.Shape;

public class DeleteCommand implements Command {
    private Drawing drawing;
    private int index;
    private Shape shape;

    public DeleteCommand(Shape shape, Drawing drawing, int index) {
        this.shape = shape;
        this.drawing = drawing;
        this.index = index;
    }
    public void execute(){
        drawing.remove(index);

    }

    @Override
    public void unexecute() {

        drawing.add(index,shape);
    }

}
