package g62368_oxono.project.View;

import g62368_oxono.project.model.Game;
import javafx.scene.control.Alert;

/**
 * The GameAlert class extends the Alert class to provide custom alert dialogs for the game.
 */
public class GameAlert extends Alert {

    /**
     * Constructs a GameAlert with the specified alert type.
     *
     * @param alertType the type of alert to be created
     */
    public GameAlert(AlertType alertType) {
        super(alertType);
    }

    /**
     * Displays an alert indicating the winner of the game.
     *
     * @param game the current game instance
     */
    public void showAlertVictory(Game game) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(game.getCurrentPlayer().getColor() + " is the Winner !!!");
        alert.show();
    }


    public void showAlertDraw(Game game) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText("It's a Draw !!!");
        alert.show();
    }
}