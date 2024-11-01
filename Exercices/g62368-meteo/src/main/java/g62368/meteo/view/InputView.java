package g62368.meteo.view;

import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.DatePicker;
import javafx.scene.layout.VBox;

import java.time.LocalDate;

public class InputView {
    private TextField textField;
    private DatePicker datePicker;
    private Button submitButton;

    public InputView() {
        this.textField = new TextField();
        this.textField.setPromptText("Entrer une ville");

        this.datePicker = new DatePicker();
        this.datePicker.setPromptText("Sélectionner une date");

        this.submitButton = new Button("Rechercher la météo");
    }

    public LocalDate getDate() {
        return datePicker.getValue();
    }

    public String getText() {
        return textField.getText();
    }

    public Button getSubmitButton() {
        return submitButton;
    }

    public VBox getView() {
        return new VBox(10, textField, datePicker, submitButton);
    }
}
