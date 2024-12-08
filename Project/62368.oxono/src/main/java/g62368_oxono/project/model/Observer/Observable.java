package g62368_oxono.project.model.Observer;

public interface Observable {

    void addObserver(Observer observer);
    void removeObserver(Observer observer);
    void notifyObservers(ObservableEvent e);
}
