package g62368_oxono.project;

import g62368_oxono.project.Controller.JeuFx;
import g62368_oxono.project.View.BoardFx;
import g62368_oxono.project.View.FxView;
import g62368_oxono.project.model.Game;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class OxonoApp extends Application {
    @Override
    public void start(Stage primaryStage) {
        Game game = new Game();
        FxView fxView = new FxView();// Taille du plateau (exemple)
        JeuFx controller = new JeuFx(game, fxView);
        controller.start();

        Scene scene = new Scene(fxView.getRoot(),800,800);

        scene.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());

        primaryStage.setTitle("Jeu OXONO");
        primaryStage.setScene(scene);
        primaryStage.show();
        controller.showStartDialog();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
