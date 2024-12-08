package g62368_oxono.project.View;

import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import g62368_oxono.project.model.Observer.Observer;

import java.util.Scanner;


public class ConsoleView {

    public Scanner scanner = new Scanner(System.in);

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

    public String getPlayerInput(Player player, String prompt) {
        System.out.println(player.getColor() + ", " + prompt);
        return scanner.nextLine();
    }

    public void showErrorMessage(String message) {
        System.out.println("Erreur : " + message);
    }

    public void showWinMessage(Player player) {
        System.out.println("Le joueur " + player.getColor() + " a gagne !");
    }

    public void showDrawMessage() {
        System.out.println("Le plateau est plein, match nul !");
    }

    public void showRestartMessage() {
        System.out.println("Le jeu peut commencer");
    }

    public void showUndoMessage() {
        System.out.println("Dernier coup joue annule.");
    }

    public void showRedoMessage() {
        System.out.println("Dernier coup joue a ete refait.");
    }

    public void showQuitMessage() {
        System.out.println("Merci d'avoir joue ! ");
    }


    public String getCommandInput() {
        System.out.println("Entrez une commande" );
        return scanner.nextLine();
    }
    public String startInput() {
        System.out.println("Entrez une commande la \" Start\" ");
        return scanner.nextLine();
    }

    public String getPawnInput() {

        System.out.println("2. Placez vos pions (PX ou PO) après le totem, avec la commande : pawn X b 3 5 ou");
        System.out.println("   - pawn X b 3 5 place le pawn X black a la position x: 3 y: 5 ");
        System.out.println("   - undo : Annule le dernier coup");
        System.out.println("   - redo : Répète le dernier coup annulé");

        return scanner.nextLine();
    }

    public String getTotemInput() {
        System.out.println(". Placez vos totems (X ou O) sur le plateau en entrant la commande : 2 3 X");
        System.out.println("   - 2 3 X: place le totem a la position x: 2 y: 3");
        System.out.println("   - undo : Annule le dernier coup");

        return scanner.nextLine();
    }



    public void     displayMenu() {
        System.out.println("====================== BIENVENUE DANS OXONO =========================");
        System.out.println("Bon jeu !");
        System.out.println("======================================================================");
        System.out.println();
    }

    public void displayRack(int[] pawnsRemaining) {
        System.out.println("Player Pink ============================Player Black :");
        System.out.println();
        System.out.println("Pawns - X: " + pawnsRemaining[0] + "                 Paws X: " + pawnsRemaining[2]);
        System.out.println("Pawns - O: " + pawnsRemaining[1] + "                 Paws O: " + pawnsRemaining[3]);
        System.out.println();
    }


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
    public void help() {
        System.out.println(" - giveup : quitter la partie " );
        System.out.println(" - undo : retourner en arriere " );
        System.out.println(" - redo : revenir en avant " );
        System.out.println(" - Placez vos pions : pawn X/O b/p 3 5");
        System.out.println(" - Placez vos totems : 2 3 X/O");
    }


}
