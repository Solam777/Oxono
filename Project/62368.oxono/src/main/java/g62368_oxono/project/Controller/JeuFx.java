

package g62368_oxono.project.Controller;

import g62368_oxono.project.View.FxView;
import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import g62368_oxono.project.model.Observer.Observer;
import g62368_oxono.project.model.Strategy.RandomStrategy;
import java.util.List;

public class JeuFx implements Observer {
    private final FxView fxView;
    private final Game game;
    RandomStrategy randomStrategy;
    private Position clickTotemPos;
    private boolean isPlacingTotem;
    private Totem lastTotemPlay;

    public JeuFx(Game game, FxView view) {
        this.game = game;
        this.fxView = view;
        randomStrategy = new RandomStrategy();
        fxView.setController(this);
        game.addObserver(this);
        setupEventHandlers();
    }

    /**
     * Sets up event handlers for the game controls.
     */
    private void setupEventHandlers() {
        // Gestionnaire pour Undo
        fxView.getUndoButton().setOnAction(e -> handleUndo());

        // Gestionnaire pour Redo
        fxView.getRedoButton().setOnAction(e -> handleRedo());

        // Gestionnaire pour Quit
        fxView.getGiveUpButton().setOnAction(e -> handleQuit());

        fxView.getPlayBot().setOnAction(e -> handlePlayBot());
    }

    /**
     * Handles the bot's turn by delegating the move to a random strategy.
     */
    private void handlePlayBot() {
        randomStrategy.play(fxView, game);
    }

    /**
     * Handles the undo action, reverting the last move made in the game.
     * Updates the game board in the FxView.
     */
    private void handleUndo() {
        try {
            game.undo();
            fxView.updateBoard(game); // Mettez à jour la vue après l'action
            FxView.setStatus("Dernier coup annulé !");
        } catch (OxonoExecption e) {
            FxView.setStatus("Impossible d'annuler : " + e.getMessage());
        }
    }

    /**
     * Handles the redo action, reapplying the last undone move.
     * Updates the game board in the FxView.
     */
    private void handleRedo() {
        try {
            game.redo();
            fxView.updateBoard(game); // Mettez à jour la vue après l'action
            FxView.setStatus("Coup refait !");
        } catch (OxonoExecption e) {
            FxView.setStatus("Impossible de refaire : " + e.getMessage());
        }
    }

    /**
     * Handles the quit action by notifying the user and ending the game.
     */
    private void handleQuit() {
        FxView.setStatus("Le joueur a abandonné la partie.");
        game.quitGame();
    }
    /**
     * Starts the game.
     */
    public void start() {
       fxView.showStartDialog(game);
    }

    /**
     * Handles a click event on the game board.
     * Manages totem movement and pawn placement based on the game state.
     *
     * @param position The Position object representing the clicked cell on the board.
     */
    public void handleClick(Position position) {
        try {
            Piece clickedPiece = game.getPiece(position);

            if (!isPlacingTotem) {
                if (clickedPiece instanceof Totem) {

                    clickTotemPos = position;
                    lastTotemPlay = (Totem) game.getPiece(clickTotemPos);

                    List<Position> accessiblePositions = game.getFreeposTotem((Totem) clickedPiece);
                    fxView.getBoardFx().highlightAccessiblePlaces(accessiblePositions);
                    FxView.setStatus("Sélectionnez une destination pour le totem");
                    return;
                }

                if (clickTotemPos != null) {
                    if (game.isValidMove(position, (Totem) game.getPiece(clickTotemPos))) {
                       game.playTotem(position, lastTotemPlay);
                        isPlacingTotem = true;
                        clickTotemPos = null;
                        fxView.getBoardFx().clearHighlights();
                        FxView.setStatus("Totem déplacé avec succès.");

                        // Afficher immédiatement les positions possibles pour le pion
                        List<Position> accessiblePositionsPawn = game.getFreeposPawn(lastTotemPlay);
                        fxView.getBoardFx().highlightAccessiblePlaces(accessiblePositionsPawn);
                        FxView.setStatus("Sélectionnez une position pour placer votre pion");
                        return;
                    }
                    else {
                        // Si le mouvement n'est pas valide, effacer la sélection
                        clickTotemPos = null;
                        fxView.getBoardFx().clearHighlights();
                        FxView.setStatus("Mouvement invalide");
                        return;
                    }
                }
            }

            if (isPlacingTotem) {
                game.playPawn(position);
                fxView.getBoardFx().clearHighlights();
                FxView.setStatus("Pion placé !");
                isPlacingTotem = false;
                return;
            }

            FxView.setStatus("Action non reconnue ou mouvement impossible.");
        } catch (OxonoExecption e) {
            FxView.setStatus("Erreur : " + e.getMessage());
        }
        fxView.getBoardFx().updateBoard(game);
        fxView.updateBoard(game);
    }

    /**
     * Updates the game view based on the observed event.
     *
     * @param event The observed event.
     */
    @Override
    public void update(ObservableEvent event) {
        switch (event) {
            case GAME_START:

            case PLACE_PAWN:
                fxView.updateBoard(game);
                fxView.updateRacks(game.getRemainingPawns());
                break;
            case UNDO:
                fxView.updateBoard(game);
                break;
            case REDO:
                fxView.updateBoard(game);
                break;
            case MOVE_TOTEM:
                fxView.updateBoard(game);
                break;
            case WIN:
                fxView.gameWin(game);
                break;
            case QUIT:
                FxView.setStatus("Le jeu est terminé.");
                break;

            case DRAW:
                fxView.gameDraw(game);
                break;
        }

    }
}