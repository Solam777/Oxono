package g62368_oxono.project.model.Commande;

import java.util.Stack;

public class CommandManager {

    /**
     * Stack holding commands that can be undone or redone.
     */
    private final Stack<Command> undoStack = new Stack<>();
    private final Stack<Command> redoStack = new Stack<>();

    /**
     * Executes a command, storing it in the undo stack and clearing the redo stack.
     *
     * @param command The command to execute.
     */
    public void executeCommand(Command command) {
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    /**
     * Undoes the last executed command by moving it from the undo stack to the redo stack
     * and invoking its `unexecute` method.
     */
    public void undo() {
        if (!undoStack.isEmpty()) {
            Command command = undoStack.pop();
            command.unexecute();
            redoStack.push(command);
        }
    }

    /**
     * Redoes the last undone command by moving it from the redo stack to the undo stack
     * and invoking its `execute` method.
     */
    public void redo() {
        if (!redoStack.isEmpty()) {
            Command command = redoStack.pop();
            command.execute();
            undoStack.push(command);
        }
    }

    /**
     * Returns the stack of commands that can be undone.
     *
     * @return the undo stack
     */
    public Stack<Command> getUndoStack() {
        return undoStack;
    }

    /**
     * Returns the stack of commands that can be redone.
     *
     * @return the redo stack
     */
    public Stack<Command> getRedoStack() {
        return redoStack;
    }


}
