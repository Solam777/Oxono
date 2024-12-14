package g62368_oxono.project.model.Strategy;

import g62368_oxono.project.View.FxView;
import g62368_oxono.project.model.*;

import java.util.List;

public class RandomStrategy {



    public void play(FxView fxView, Game game){
        try {
            // Choix aléatoire du totem (O ou X)
            int indexChoice = getRandom(0, 1);
            Totem lastTotemPlay = new Totem(indexChoice == 0 ? Mark.O : Mark.X);

            List<Position> positionsValideTotem = game.getFreeposTotem(lastTotemPlay);

            // Vérification si des positions valides existent pour le totem
            if (positionsValideTotem.isEmpty()) {
                FxView.setStatus("Le bot ne peut pas jouer : aucune position valide pour le totem");
                return;
            }

            // Choix et placement du totem
            boolean totemPlaced = false;
            while (!totemPlaced && !positionsValideTotem.isEmpty()) {
                int indexTotem = getRandom(0, positionsValideTotem.size() - 1);
                Position posTotem = positionsValideTotem.get(indexTotem);

                if (game.isValidMove(posTotem, lastTotemPlay)) {
                    fxView.updateBoard(game);
                    game.playTotem(posTotem, lastTotemPlay);
                    fxView.updateBoard(game);
                    totemPlaced = true;
                } else {
                    positionsValideTotem.remove(indexTotem);
                }
            }

            if (!totemPlaced) {
                FxView.setStatus("Le bot n'a pas pu placer le totem");
                return;
            }

            // Récupération et vérification des positions valides pour le pion
            List<Position> positionsValidePawn = game.getFreeposPawn(lastTotemPlay);
            if (positionsValidePawn.isEmpty()) {
                FxView.setStatus("Le bot ne peut pas placer de pion : aucune position valide");
                return;
            }

            // Choix et placement du pion
            boolean pawnPlaced = false;
            while (!pawnPlaced && !positionsValidePawn.isEmpty()) {
                int indexpawn = getRandom(0, positionsValidePawn.size() - 1);
                Position posPawn = positionsValidePawn.get(indexpawn);

                try {
                    game.playPawn(posPawn);
                    fxView.updateBoard(game);
                    pawnPlaced = true;
                } catch (OxonoExecption e) {
                    positionsValidePawn.remove(indexpawn);
                }
            }

            if (!pawnPlaced) {
                FxView.setStatus("Le bot n'a pas pu placer le pion");
                return;
            }
            fxView.updateBoard(game);
            FxView.setStatus("Le bot a joué son tour");

        } catch (OxonoExecption e) {
            FxView.setStatus("Erreur lors du tour du bot : " + e.getMessage());
        }
    }

    public static int getRandom(int min, int max) {
        return min + (int)(Math.random() * ((max - min) + 1));
    }

}
