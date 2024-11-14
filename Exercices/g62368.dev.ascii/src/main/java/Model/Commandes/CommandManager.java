package Model.Commandes;

import java.util.Stack;

public class CommandManager {
    private Stack<Command> undoStack;
    private Stack<Command> redoStack;

    public CommandManager() {
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
    }

    public void do_(Command command){
        command.execute();
        undoStack.push(command);
        redoStack.clear();
    }

    public void undo() {
        if (!undoStack.isEmpty()){
           redoStack.push( undoStack.pop()).unexecute();
        }
    }

    public  void redo(){
        if (!redoStack.isEmpty()){
           undoStack.push(redoStack.pop()).execute();
        }
    }

}
