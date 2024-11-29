package g62368_oxono.project;

import g62368_oxono.project.Controller.JeuConsole;
import g62368_oxono.project.View.ConsoleView;
import g62368_oxono.project.model.Game;

public class ConsoleAApp {

    public static void main(String[] args) {
        Game game = new Game();
        ConsoleView consoleView = new ConsoleView();
        JeuConsole jeuConsole = new JeuConsole(game, consoleView);
        jeuConsole.start();
    }
}
