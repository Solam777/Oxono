package g62368_oxono.project.model.Strategy;

import g62368_oxono.project.View.FxView;
import g62368_oxono.project.model.*;

import java.util.List;

/**
 * Implements a random strategy for a bot to play a turn in the game.
 * The bot randomly selects valid positions for placing a totem and a pawn.
 */
public class RandomStrategy {

    /**
     * Makes the bot play a turn using a random strategy.
     * The bot selects a random totem (X or O), places it in a valid position,
     * and then places a pawn in a valid position associated with the totem.
     *
     * @param fxView The game view to update the UI and display the bot's moves.
     * @param game   The game instance on which the bot will perform actions.
     */
    public void play(FxView fxView, Game game) {
        try {
            // Randomly choose a totem (O or X)
            int indexChoice = getRandom(0, 1);
            Totem lastTotemPlay = new Totem(indexChoice == 0 ? Mark.O : Mark.X);

            List<Position> positionsValideTotem = game.getFreeposTotem(lastTotemPlay);

            // Check if there are valid positions for the totem
            if (positionsValideTotem.isEmpty()) {
                FxView.setStatus("The bot cannot play: no valid positions for the totem.");
                return;
            }

            // Place the totem in a valid position
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
                FxView.setStatus("The bot could not place the totem.");
                return;
            }

            // Retrieve valid positions for the pawn
            List<Position> positionsValidePawn = game.getFreeposPawn(lastTotemPlay);
            if (positionsValidePawn.isEmpty()) {
                FxView.setStatus("The bot cannot place a pawn: no valid positions.");
                return;
            }

            // Place the pawn in a valid position
            boolean pawnPlaced = false;
            while (!pawnPlaced && !positionsValidePawn.isEmpty()) {
                int indexPawn = getRandom(0, positionsValidePawn.size() - 1);
                Position posPawn = positionsValidePawn.get(indexPawn);

                try {
                    game.playPawn(posPawn);
                    fxView.updateBoard(game);
                    pawnPlaced = true;
                } catch (OxonoExecption e) {
                    positionsValidePawn.remove(indexPawn);
                }
            }

            if (!pawnPlaced) {
                FxView.setStatus("The bot could not place the pawn.");
                return;
            }

            fxView.updateBoard(game);
            FxView.setStatus("The bot has completed its turn.");

        } catch (OxonoExecption e) {
            FxView.setStatus("Error during the bot's turn: " + e.getMessage());
        }
    }

    /**
     * Generates a random integer between the specified minimum and maximum values (inclusive).
     *
     * @param min The minimum value (inclusive).
     * @param max The maximum value (inclusive).
     * @return A random integer between min and max.
     */
    public static int getRandom(int min, int max) {
        return min + (int) (Math.random() * ((max - min) + 1));
    }
}
