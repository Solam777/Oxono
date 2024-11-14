package Model.Commandes;

public interface Command {
    public void execute();
    public void unexecute();
}
