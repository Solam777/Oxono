package g62368_oxono.project.Controller;

import g62368_oxono.project.View.ConsoleView;
import g62368_oxono.project.model.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class JeuConsole {
    private Game game;
    private ConsoleView view;
    public JeuConsole(Game game, ConsoleView view) {
        this.game = new Game();
        this.view = view;
    }


    public void start() {
        view.displayStart();
        int boardsize = view.getBoardSize();
        boolean quit = false;
        boolean started = false;
        while (!quit) {
            String input = view.startInput();
            if (input.equals("start")) {
                started = true;
                game.initializeGame(boardsize);
                play();
            }
        }
    }

    private void play() {
        boolean victory = false;
        do {
            view.displayMenu();
            view.displayBoard(game);
            view.displayRack(game.getRemainingPawns());

            placeTotem();
            view.displayBoard(game);
            view.getCommandInput();
            playPawn();

            victory = game.checkWin();
            if (!victory) {
                if (game.isDraw()) {
                    // Égalité détectée
                    view.showDrawMessage();
                    break;
                }
                game.switchPlayer();
            } else {

                view.showWinMessage(game.getCurrentPlayer());
            }
        } while (!victory && !game.isDraw());
    }


    private void playPawn() {
        // Définitions des regex pour les différentes commandes
        String regexPawn = "^pawn\\s([XO])\\s([bBpP])\\s(\\d+)\\s(\\d+)$";
        String regexUndo = "^undo$";
        String regexRedo = "^redo$";
        String regexGiveup = "^giveup$";

        // Compilation des patterns
        Pattern patternPawn = Pattern.compile(regexPawn);
        Pattern patternUndo = Pattern.compile(regexUndo);
        Pattern patternRedo = Pattern.compile(regexRedo);
        Pattern patternGiveup = Pattern.compile(regexGiveup);

        while (true) {
            // Lecture de l'entrée utilisateur
            String input = view.getPawnInput();

            // Création des matchers pour chaque commande
            Matcher matcherPawn = patternPawn.matcher(input);
            Matcher matcherUndo = patternUndo.matcher(input);
            Matcher matcherRedo = patternRedo.matcher(input);
            Matcher matcherGiveup = patternGiveup.matcher(input);

            try {
                // Commande "pawn"
                if (matcherPawn.matches()) {
                    String mark = matcherPawn.group(1); // X ou O
                    String color = matcherPawn.group(2); // b ou p
                    int x = Integer.parseInt(matcherPawn.group(3)); // Coordonnée x
                    int y = Integer.parseInt(matcherPawn.group(4)); // Coordonnée y

                    // Placement du pion
                    placePawnOX(mark, color, x, y);
                    break; // Sortie de la boucle après un placement valide

                    // Commande "undo"
                } else if (matcherUndo.matches()) {
                    game.undo();
                    view.showUndoMessage();
                    view.displayBoard(game);

                    // Commande "redo"
                } else if (matcherRedo.matches()) {
                    game.redo();
                    view.showRedoMessage();
                    view.displayBoard(game);

                    // Commande "giveup"
                } else if (matcherGiveup.matches()) {
                    view.showQuitMessage();
                    System.exit(0); // Quitte proprement le programme

                    // Entrée invalide
                } else {
                    view.showErrorMessage("Commande invalide. Veuillez réessayer.");
                }
            } catch (OxonoExecption e) {
                // Gestion des erreurs spécifiques au jeu
                view.showErrorMessage("Erreur : " + e.getMessage());
            }
        }
    }



    private void placeTotem(){
        String regexTotem = "^(\\d+)\\s(\\d+)\\s([XO])$";
        String regexundo = "^undo$";
        String regexredo = "^redo$";
        String regexgiveup = "^giveup$";

        Pattern patternUndo = Pattern.compile(regexundo);
        Pattern patternRedo = Pattern.compile(regexredo);
        Pattern patternGiveup = Pattern.compile(regexgiveup);

        Pattern patternTotem = Pattern.compile(regexTotem);
        while (true) {
            String input = view.getTotemInput();
            Matcher matcherTotem = patternTotem.matcher(input);
            Matcher matcherUndo = patternUndo.matcher(input);
            Matcher matcherRedo = patternRedo.matcher(input);
            Matcher matcherGiveup = patternGiveup.matcher(input);
            if (matcherTotem.matches()) {
                int x = Integer.parseInt(matcherTotem.group(1)); // Coordonnée x
                int y = Integer.parseInt(matcherTotem.group(2)); // Coordonnée y
                String mark = matcherTotem.group(3); // X ou O

                placeTotem(mark,x,y);
                break;
            }
            else if (matcherUndo.matches()) {

                game.undo();
                view.showUndoMessage();
                view.displayBoard(game);

            } else if (matcherGiveup.matches()) {
                System.exit(0);
                view.showQuitMessage();

            } else if (matcherRedo.matches()) {
                game.redo();
                view.showRedoMessage();
                view.displayBoard(game);
            }

            else {
                System.out.println("commande not valid");
            }
        }
    }
    private void playUndoOrRedoOrQuit() {


    }

    private void placePawnOX(String mark, String color, int x, int y) {
        if (mark.equalsIgnoreCase("x")) {
            if (color.equalsIgnoreCase("b")) {
                game.playPawn(new Position(x, y), new Pawn(Mark.X, Color.BLACK));
            } else if (color.equalsIgnoreCase("p")) {
                game.playPawn(new Position(x, y), new Pawn(Mark.X, Color.PINK));
            } else {
                System.out.println("Couleur invalide.");
            }
        } else if (mark.equalsIgnoreCase("o")) {
            if (color.equalsIgnoreCase("b")) {
                game.playPawn(new Position(x, y), new Pawn(Mark.O, Color.BLACK));
            } else if (color.equalsIgnoreCase("p")) {
                game.playPawn(new Position(x, y), new Pawn(Mark.O, Color.PINK));
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
        } else if (mark.equalsIgnoreCase("o")) {
            game.playTotem(new Position(x, y), new Totem(Mark.O));
        } else {
            System.out.println("Marque invalide.");
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.initializeGame(6);
        ConsoleView consoleView = new ConsoleView();
        game.playPawn(new Position(2,3),new Pawn(Mark.X,Color.BLACK));
        consoleView.displayBoard(game);
        game.playTotem(new Position(3,4),new Totem(Mark.O));
        game.playPawn(new Position(4,4),new Pawn(Mark.O,Color.BLACK));

        consoleView.displayBoard(game);
    }



}
