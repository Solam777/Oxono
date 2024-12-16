package g62368_oxono.project.Controller;

import g62368_oxono.project.View.ConsoleView;
import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import g62368_oxono.project.model.Observer.Observer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Constructs a JeuConsole object with a specified game and console view.
 * Initializes a new game instance and sets up the observer pattern.
 *
 * param game the Game instance to be used
 * param view the ConsoleView instance to interact with the user
 */
public class JeuConsole implements Observer {
    private Game game;
    private ConsoleView view;
    public JeuConsole(Game game, ConsoleView view) {
        this.game = new Game();
        this.view = view;
        game.addObserver(this);
    }

    /**
     * Starts the game by initializing it, getting user input, and controlling the main game loop.
     * The game continues until the user decides to quit.
     */
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
    /**
     * Runs the main game logic, handling turns, checking for victory, draws, and player switching.
     * Displays the game board and manages errors.
     */
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

    /**
     * Places a pawn on the game board at the specified coordinates.
     *
     * @param x the x-coordinate where the pawn will be placed
     * @param y the y-coordinate where the pawn will be placed
     */
    private void placePawnOX(int x, int y) {
        game.playPawn(new Position(x, y));
    }

    /**
     * Places a totem on the game board at the specified coordinates with a given mark (X or O).
     *
     * @param mark the mark (X or O) representing the totem type
     * @param x the x-coordinate where the totem will be placed
     * @param y the y-coordinate where the totem will be placed
     */
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


    /**
     * Requests an action from the user and executes it if valid.
     * Parses user input for commands and handles them accordingly.
     */
    private void whatdo() {
        String regexTotem = "^(\\d+)\\s(\\d+)\\s([XO])$";
        Pattern patternTotem = Pattern.compile(regexTotem);

        String regexPawn = "^pawn\\s(\\d+)\\s(\\d+)$";
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


    /**
     * Handles the "undo" command by reverting the last game action if possible.
     */
    private void handleUndo() {
        if (game.canUndo()) {
            game.undo();
            view.displayBoard(game);
            view.showUndoMessage();
        } else {
            view.showErrorMessage("Aucune action à annuler !");
        }
    }

    /**
     * Handles the "redo" command by re-executing the last undone game action if possible.
     */
    private void handleRedo() {
        if (game.canRedo()) {
            game.redo();
            view.displayBoard(game);
            view.showRedoMessage();
        } else {
            view.showErrorMessage("Aucune action à rejouer !");
        }
    }

    /**
     * Handles the "give up" command by displaying a quit message and terminating the game.
     */
    private void handleGiveUp() {
        view.showQuitMessage();
        System.exit(0);
    }

    /**
     * Processes a totem placement command from user input using a regex matcher.
     *
     * @param matcherTotem the Matcher object containing parsed totem command information
     */
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

    /**
     * Processes a pawn placement command from user input using a regex matcher.
     *
     * @param matcherPawn the Matcher object containing parsed pawn command information
     */
    private void handlePawnCommand(Matcher matcherPawn) {

        int x = Integer.parseInt(matcherPawn.group(1));
        int y = Integer.parseInt(matcherPawn.group(2));

        try {
            placePawnOX(x, y);
            view.displayBoard(game);
            view.displayRack(game.getRemainingPawns());
        } catch (OxonoExecption e) {
            view.showErrorMessage("Erreur : " + e.getMessage());
        }
    }

    /**
     * Updates the game view based on observable events from the game model.
     *
     * @param event the ObservableEvent instance representing the type of event that occurred
     */
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



