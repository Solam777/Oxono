package Controller;
import Model.AsciiPaint;
import Model.Point;
import View.View;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AsciiController {
    AsciiPaint tab = new AsciiPaint(50, 50);
    View vue = new View();

    public AsciiController(AsciiPaint tab, View vue) {
        this.tab = tab;
        this.vue = vue;
    }

    public void start() {
        Scanner input = new Scanner(System.in);
        String choice;
        System.out.println("Tapez 1 pour ajouter des formes ");
        System.out.println("Tapez 2 pour afficher votre forme ");
        System.out.println("Tapez 3 pour afficher votre liste de formes ");
        System.out.println("Tapez 4 pour bouger une forme ");
        System.out.println("Tapez 5 pour supprimer une forme ");
        System.out.println("Tapez -1 pour quitter");

        while (true) {
            choice = input.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Tapez -1 pour arrêter");

                    String regexCercle = "^add\\s+circle\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\w)$";
                    String regexRectangle = "^add\\s+rectangle\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\w)$";
                    String regexCarre = "^add\\s+square\\s+(\\d+)\\s+(\\d+)\\s+(\\d+)\\s+(\\w)$";

                    Pattern patternCercle = Pattern.compile(regexCercle);
                    Pattern patternRectangle = Pattern.compile(regexRectangle);
                    Pattern patternCarre = Pattern.compile(regexCarre);

                    String forme;
                    System.out.println("Si c'est un cercle, il doit être écrit sous cette forme : add circle 5 3 1 c");
                    System.out.println("Si c'est un rectangle, il doit être écrit sous cette forme : add rectangle 10 10 5 20 r");
                    System.out.println("Si c'est un carré, il doit être écrit sous cette forme : add square 10 10 5 r");

                    while (true) {
                        forme = input.nextLine();

                        if (forme.equals("-1")) {
                            System.out.println("Arrêt de l'ajout de forme.");
                            System.out.println("Tapez 1 pour ajouter des formes ");
                            System.out.println("Tapez 2 pour afficher votre forme ");
                            System.out.println("Tapez 3 pour afficher votre liste de formes ");
                            System.out.println("Tapez 4 pour bouger une forme ");
                            System.out.println("Tapez 5 pour supprimer une forme ");
                            System.out.println("Tapez -1 pour quitter");
                            break;
                        }

                        Matcher matcherCercle = patternCercle.matcher(forme);
                        Matcher matcherRectangle = patternRectangle.matcher(forme);
                        Matcher matcherCarre = patternCarre.matcher(forme);

                        if (matcherCercle.matches()) {
                            int x = Integer.parseInt(matcherCercle.group(1));
                            int y = Integer.parseInt(matcherCercle.group(2));
                            int radius = Integer.parseInt(matcherCercle.group(3));
                            char color = matcherCercle.group(4).charAt(0);

                            tab.addCircle(new Point(x, y), radius, color);
                            System.out.println("Cercle ajouté : (" + x + ", " + y + "), rayon: " + radius + ", couleur: " + color);

                        } else if (matcherRectangle.matches()) {
                            int upperLeftX = Integer.parseInt(matcherRectangle.group(1));
                            int upperLeftY = Integer.parseInt(matcherRectangle.group(2));
                            int width = Integer.parseInt(matcherRectangle.group(3));
                            int height = Integer.parseInt(matcherRectangle.group(4));
                            char color = matcherRectangle.group(5).charAt(0);

                            tab.addRectangle(upperLeftX, upperLeftY, width, height, color);
                            System.out.println("Rectangle ajouté : (" + upperLeftX + ", " + upperLeftY + "), largeur: " + width + ", hauteur: " + height + ", couleur: " + color);

                        } else if (matcherCarre.matches()) {
                            int upperLeftX = Integer.parseInt(matcherCarre.group(1));
                            int upperLeftY = Integer.parseInt(matcherCarre.group(2));
                            int side = Integer.parseInt(matcherCarre.group(3));
                            char color = matcherCarre.group(4).charAt(0);

                            tab.addSquare(upperLeftX, upperLeftY, side, color);
                            System.out.println("Carré ajouté : (" + upperLeftX + ", " + upperLeftY + "), côté: " + side + ", couleur: " + color);

                        } else {
                            System.out.println("Commande invalide, réessayez.");
                        }
                    }
                    break;

                case "2":
                    System.out.println("Affichage de la forme...");
                    // À préciser : si vous voulez afficher une seule forme, ajouter un choix pour sélectionner l'index
                    vue.display(tab);
                    break;

                case "3":
                    System.out.println("Affichage de la liste des formes...");
                    vue.display(tab);
                    break;

                case "4":
                    System.out.println("l'index ?");
                    int index = input.nextInt();
                    System.out.println("dx ?");
                    double dx = input.nextDouble();
                    System.out.println("dy ?");
                    double dy = input.nextDouble();

                    tab.moveShape(index, dx, dy);
                    break;

                case "5":
                    System.out.println("la pos ?");
                    int pos = input.nextInt();
                    tab.removeShape(pos);
                    break;

                case "-1":
                    System.out.println("Quitter...");
                    input.close();  // Scanner fermé uniquement lors de la sortie
                    return;

                default:
                    System.out.println("Option invalide. Réessayez.");
                    break;
            }
        }
    }
}
