package Model.Commandes;

import Model.Drawing;
import Model.Group;
import Model.Shape;

import java.util.List;

public class UngroupCommande implements Command {
    private int index;
    private Drawing drawing;
    List<Shape> shapesInGroup;
    private Group group;

    public UngroupCommande(int index, Drawing drawing) {
        this.index = index;
        this.drawing = drawing;
    }

    @Override
    public void execute() {
        Shape forme = drawing.getShapeAt(index);

        if (forme instanceof Group) {
            group = (Group) forme;
            shapesInGroup = group.getShapes();
            drawing.remove(index);
            for (Shape s : shapesInGroup) {
                drawing.add(s);
            }
            System.out.println("the group is dissociate " + shapesInGroup.size() + " formes.");
        } else {
            System.out.println("nothing find at : " + index);
        }


    }

    @Override
    public void unexecute() {
        if (group != null) {
            for (Shape s : shapesInGroup) {
                drawing.remove(s);
            }
            drawing.add(group);
            System.out.println("group re-associate");
        }
    }
}
