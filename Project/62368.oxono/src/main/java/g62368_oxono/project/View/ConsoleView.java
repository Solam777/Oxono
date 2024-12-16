package g62368_oxono.project.View;

import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import g62368_oxono.project.model.Observer.Observer;

import java.util.Scanner;


public class ConsoleView {

    public Scanner scanner = new Scanner(System.in);


    /**
     * Displays the current state of the game board in the console.
     *
     * @param game the {@code Game} object representing the current state of the game
     */
    public void displayBoard(Game game) {
        for (int i = 0; i < game.getSize(); i++) {
            System.out.println("+---".repeat(game.getSize()) + "+");
            for (int j = 0; j < game.getSize(); j++) {
                Piece piece = game.getPiece(new Position(i, j));
                if (piece != null) {
                    if (game.isTotem(piece)) {
                        Totem totem = (Totem) piece;
                        System.out.print("| " + totem.toString() + " ");
                    } else {
                        Pawn pawn = (Pawn) piece;
                        System.out.print("| " + pawn.toString() + " ");
                    }
                } else {
                    System.out.print("|   ");
                }
            }
            System.out.println("|");
        }
        System.out.println("+---".repeat(game.getSize()) + "+");

        System.out.println();
    }

    /**
     * Displays an error message in the console.
     *
     * @param message the error message to display
     */
    public void showErrorMessage(String message) {
        System.out.println("Erreur : " + message);
    }

    /**
     * Displays a winning message for the given player.
     *
     * @param player the {@code Player} who won
     */
    public void showWinMessage(Player player) {
        System.out.println("Le joueur " + player.getColor() + " a gagne !");
    }

    /**
     * Displays a message indicating a draw.
     */
    public void showDrawMessage() {
        System.out.println("Le plateau est plein, match nul !");
    }


    /**
     * Displays a message indicating the game has restarted.
     */
    public void showRestartMessage() {
        System.out.println("Le jeu peut commencer");
    }

    /**
     * Displays a message indicating the last move was undone.
     */
    public void showUndoMessage() {
        System.out.println("Dernier coup joue annule.");
    }

    /**
     * Displays a message indicating the last move was redone.
     */
    public void showRedoMessage() {
        System.out.println("Dernier coup joue a ete refait.");
    }

    /**
     * Displays a message indicating the game should be quit.
     */
    public void showQuitMessage() {
        System.out.println("Merci d'avoir joue ! ");
    }

    /**
     * Prompts the user for a command input and returns it.
     *
     * @return the user input as a {@code String}
     */
    public String getCommandInput() {
        System.out.println("Entrez une commande" );
        return scanner.nextLine();
    }

    /**
     * Displays the menu of commands for the user.
     */
    public void     displayMenu() {
        System.out.println("====================== BIENVENUE DANS OXONO =========================");
        System.out.println("Bon jeu !");
        System.out.println("======================================================================");
        System.out.println();
    }

    /**
     * Displays the current rack of pawns for each player.
     *
     * @param pawnsRemaining an array of integers representing the number of pawns
     *                       remaining for each player in the format:
     *                       [Player 1 - X, Player 1 - O, Player 2 - X, Player 2 - O]
     */
    public void displayRack(int[] pawnsRemaining) {
        System.out.println("Player Pink ============================Player Black :");
        System.out.println();
        System.out.println("Pawns - X: " + pawnsRemaining[0] + "                 Paws X: " + pawnsRemaining[2]);
        System.out.println("Pawns - O: " + pawnsRemaining[1] + "                 Paws O: " + pawnsRemaining[3]);
        System.out.println();
    }

    /**
     * Prompts the user to choose the size of the game board.
     * Ensures the input is a valid integer between 6 and 12.
     *
     * @return the chosen board size as an integer
     */
    public int getBoardSize() {
        while (true) {
            System.out.println("Choisissez la taille du plateau (entre 6 et 12) :");
            try {
                int size = Integer.parseInt(scanner.nextLine());
                if (size >= 6 && size <= 12) {
                    return size;
                }
                System.out.println("Veuillez entrer une taille valide entre 6 et 12.");
            } catch (NumberFormatException e) {
                System.out.println("Veuillez entrer un nombre valide.");
            }
        }
    }

    /**
     * Displays a help menu with a list of available commands.
     */
    public void help() {
        System.out.println(" - giveup : quitter la partie " );
        System.out.println(" - undo : retourner en arriere " );
        System.out.println(" - redo : revenir en avant " );
        System.out.println(" - Placez vos pions : pawn 3 5");
        System.out.println(" - Placez vos totems : 2 3 X/O");
    }


}
