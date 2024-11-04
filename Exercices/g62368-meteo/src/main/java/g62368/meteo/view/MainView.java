package g62368.meteo.view;

import g62368.meteo.controller.Controller;
import g62368.meteo.model.WeatherObject;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.time.LocalDate;

public class MainView {
    private Stage stage;
    private InputView inputView;
    private WeatherView weatherView;
    private Controller controller;

    public MainView(Stage stage ) {
        this.stage = stage;
        this.inputView = new InputView();
        this.weatherView = new WeatherView();
        BorderPane root = new BorderPane();
        root.setTop(inputView.getView());
        root.setCenter(weatherView.getView());

        Scene scene = new Scene(root, 400, 300);
        stage.setScene(scene);
        stage.setTitle("Météo");
        stage.show();
    }



    public void setControlle(Controller controller){
        this.controller = controller;
        inputView.getSubmitButton().setOnAction(e -> {
            String address = inputView.getText();
            LocalDate date = inputView.getDate();
            controller.actionFetch(address, date);
        });


    }
    public void update (WeatherObject value){
        weatherView.setName(value.locality());
        weatherView.setTempMin(value.tempMin());
        weatherView.setTempMax(value.tempMax());
//        weatherView.setImage(value.weatherCode());

    }
    public void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
