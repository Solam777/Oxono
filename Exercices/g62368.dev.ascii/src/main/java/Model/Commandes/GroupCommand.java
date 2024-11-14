package Model.Commandes;

import Model.Drawing;
import Model.Group;
import Model.Shape;

import java.util.ArrayList;
import java.util.List;

public class GroupCommand implements Command {
    private Drawing drawing;
    private char color;
    private  List<Integer> indices = new ArrayList<>();
    private List<Shape> originalShapes = new ArrayList<>();
    Group group;


    public GroupCommand(Drawing drawing, List<Integer> indices, char color) {
        this.drawing = drawing;

        this.indices = indices;
        this.color = color;
    }

    @Override
    public void execute() {
        group = new Group(color);
        List<Shape> shapesToRemove = new ArrayList<>();

        for (int index : indices) {
            Shape forme = drawing.getShapeAt(index);
            if (forme != null) {
                group.addshape(forme);
                shapesToRemove.add(forme);
                originalShapes.add(forme);           }
        }
        for (Shape forme : shapesToRemove) {
            drawing.remove(forme);
        }
        drawing.add(group);

    }

    @Override
    public void unexecute() {
        if (group != null){
            drawing.remove(group);
            for (Shape forme : originalShapes) {
                drawing.add(forme);
            }

        }




    }
}
