package g62368.meteo;

import g62368.meteo.controller.Controller;
import g62368.meteo.model.Model;
import g62368.meteo.view.MainView;
import javafx.application.Application;
import javafx.stage.Stage;

public class Main extends Application {

    public static void main(String[] args) {
        launch(args);

    }

    @Override
    public void start(Stage stage) throws Exception {
        Model model = new Model();
        MainView mainView = new MainView(stage);
        Controller controller = new Controller(model, mainView);
        mainView.setControlle(controller);
        stage.setTitle("Weather Application");
        stage.show();


    }
}
