package g62368_oxono.project.Controller;

import g62368_oxono.project.View.ConsoleView;
import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import g62368_oxono.project.model.Observer.Observer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JeuConsole implements Observer {
    private Game game;
    private ConsoleView view;
    public JeuConsole(Game game, ConsoleView view) {
        this.game = new Game();
        this.view = view;
        game.addObserver(this);
    }


    public void start() {
    int boardsize = view.getBoardSize();
    boolean quit = false;

    game.initializeGame(boardsize);
    while (!quit) {
        String input = view.getCommandInput();

            play();
        if (input.equalsIgnoreCase("quit")) {
            quit = true;
        }
    }
}



    private void play() {
        boolean victory = false;
        do {
            try {
                view.displayMenu();
                view.displayRack(game.getRemainingPawns());
                view.displayBoard(game);
                whatdo();

                victory = game.checkWin();
                if (victory) {
                    view.showWinMessage(game.getCurrentPlayer());
                } else if (game.isDraw()) {
                    view.showDrawMessage();
                    break;
                } else {
                    game.switchPlayer();
                }
            } catch (OxonoExecption e) {
                view.showErrorMessage("Erreur : " + e.getMessage());
            }
        } while (!victory && !game.isDraw());
    }


    private void placePawnOX(String mark, String color, int x, int y) {
        if (mark.equalsIgnoreCase("x")) {
            if (color.equalsIgnoreCase("b")) {
                game.playPawn(new Position(x, y));
                System.out.println("placement du pion X en (" + x + "," + y + ")");
            } else if (color.equalsIgnoreCase("p")) {

                game.playPawn(new Position(x, y));
                System.out.println("Placement du pion X en (" + x + "," + y + ")");
            } else {
                System.out.println("Couleur invalide.");
            }
        } else if (mark.equalsIgnoreCase("o")) {
            if (color.equalsIgnoreCase("b")) {
                game.playPawn(new Position(x, y));
                System.out.println("Placement du pion O en (" + x + "," + y + ")");
            } else if (color.equalsIgnoreCase("p")) {
                game.playPawn(new Position(x, y));
                System.out.println("Placement du pion O en (" + x + "," + y + ")");
            } else {
                System.out.println("Couleur invalide.");
            }
        } else {
            System.out.println("Marque invalide.");
        }
    }


    private void placeTotem(String mark, int x, int y) {
        if (mark.equalsIgnoreCase("x")) {
            game.playTotem(new Position(x, y), new Totem(Mark.X));
            System.out.println("Placement du totem X en (" + x + "," + y + ")");
        } else if (mark.equalsIgnoreCase("o")) {
            game.playTotem(new Position(x, y), new Totem(Mark.O));
            System.out.println("Placement du totem X en (" + x + "," + y + ")");
        } else {
            System.out.println("Marque invalide.");
        }
    }


    /*input qui demande quoi faire a lutilsateur et l'execute si cest correct */
    private void whatdo() {
        String regexTotem = "^(\\d+)\\s(\\d+)\\s([XO])$";
        Pattern patternTotem = Pattern.compile(regexTotem);

        String regexPawn = "^pawn\\s([XO])\\s([bBpP])\\s(\\d+)\\s(\\d+)$";
        Pattern patternPawn = Pattern.compile(regexPawn);

        boolean isRunning = true;

        while (isRunning) {
            String input = view.getCommandInput();

            if (input.equalsIgnoreCase("undo")) {
                handleUndo();
            } else if (input.equalsIgnoreCase("redo")) {
                handleRedo();
            } else if (input.equalsIgnoreCase("giveup")) {
                    handleGiveUp();
            } else if (input.equalsIgnoreCase("help")) {
                view.help();
            } else {

                Matcher matcherTotem = patternTotem.matcher(input);
                Matcher matcherPawn = patternPawn.matcher(input);

                if (matcherTotem.matches()) {
                    handleTotemCommand(matcherTotem);
                } else if (matcherPawn.matches()) {
                    handlePawnCommand(matcherPawn);
                } else {
                    view.showErrorMessage("Commande invalide. Veuillez réessayer.");
                }
            }
        }
    }

    // Gestion des commandes
    private void handleUndo() {
        if (game.canUndo()) {
            game.undo();
            view.displayBoard(game);
            view.showUndoMessage();
        } else {
            view.showErrorMessage("Aucune action à annuler !");
        }
    }

    private void handleRedo() {
        if (game.canRedo()) {
            game.redo();
            view.displayBoard(game);
            view.showRedoMessage();
        } else {
            view.showErrorMessage("Aucune action à rejouer !");
        }
    }

    private void handleGiveUp() {
        view.showQuitMessage();
        System.exit(0);
    }

    private void handleTotemCommand(Matcher matcherTotem) {
        int x = Integer.parseInt(matcherTotem.group(1));
        int y = Integer.parseInt(matcherTotem.group(2));
        String mark = matcherTotem.group(3);

        try {
            placeTotem(mark, x, y);
            view.displayBoard(game);


        } catch (OxonoExecption e) {
            view.showErrorMessage("Erreur : " + e.getMessage());
        }
    }

    private void handlePawnCommand(Matcher matcherPawn) {
        String mark = matcherPawn.group(1);
        String color = matcherPawn.group(2);
        int x = Integer.parseInt(matcherPawn.group(3));
        int y = Integer.parseInt(matcherPawn.group(4));

        try {
            placePawnOX(mark, color, x, y);
            view.displayBoard(game);
            view.displayRack(game.getRemainingPawns());
        } catch (OxonoExecption e) {
            view.showErrorMessage("Erreur : " + e.getMessage());
        }
    }


    @Override
    public void update(ObservableEvent event) {
        switch (event) {
            case WIN:
                view.showWinMessage(game.getCurrentPlayer());
                break;

            case GAME_START:
                view.showRestartMessage();
                break;
            case UNDO:
                view.showUndoMessage();
                view.displayBoard(game);
                break;
            case REDO:
                view.showRedoMessage();
                view.displayBoard(game);

                break;
            case DRAW:
                view.showQuitMessage();
                System.exit(0);
                break;
            case MOVE_TOTEM:
                view.displayBoard(game);
                break;
            case PLACE_PAWN:
                view.displayBoard(game);
                break;

            case QUIT :
                view.showQuitMessage();
                System.exit(0);
                break;
        }

    }
}



