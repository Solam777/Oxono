package g62368_oxono.project.model.Commande;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoStack = new Stack<>();

    public boolean isIsplacepawn() {
        return isplacepawn;
    }

    private Stack<Command> redoStack = new Stack<>();
    private boolean isplacepawn = false;

    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
        isplacepawn = true;
    }

    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.unexecute();
            redoStack.push(command);
        }
    }

    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }


    public Stack<Command> getUndoStack() {
        return undoStack;
    }

    public Stack<Command> getRedoStack() {
        return redoStack;
    }
}
