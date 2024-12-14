package g62368_oxono.project.View;

import g62368_oxono.project.model.Game;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

public class GameAlert extends Alert {


    public GameAlert(AlertType alertType) {
        super(alertType);
    }

    public GameAlert(AlertType alertType, String contentText, ButtonType... buttons) {
        super(alertType, contentText, buttons);
    }

    public void showAlert(Game game ) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setContentText(game.getCurrentPlayer().getColor() + " is the Winner !!!");
        alert.show();
    }
}
