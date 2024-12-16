package g62368_oxono.project.model.Observer;

/**
 * The Observable interface provides methods for managing observers and notifying them of events.
 */
public interface Observable {

    /**
     * Adds an observer to the list of observers.
     *
     * @param observer the observer to be added
     */
    void addObserver(Observer observer);

    /**
     * Removes an observer from the list of observers.
     *
     * @param observer the observer to be removed
     */
    void removeObserver(Observer observer);

    /**
     * Notifies all observers of a specific event.
     *
     * @param e the event to notify observers about
     */
    void notifyObservers(ObservableEvent e);
}