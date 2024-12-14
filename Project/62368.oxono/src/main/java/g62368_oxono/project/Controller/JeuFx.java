

package g62368_oxono.project.Controller;

import g62368_oxono.project.View.FxView;
import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import g62368_oxono.project.model.Observer.Observer;
import g62368_oxono.project.model.Strategy.RandomStrategy;

import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

public class JeuFx implements Observer {
    private final FxView fxView;
    private Game game;
    RandomStrategy randomStrategy;
    private Position clickTotemPos;
    private boolean isPlacingTotem;
    private Totem lastTotemPlay;
    private List<Position> accessiblePositions;

    public JeuFx(Game game, FxView view) {
        this.game = game;
        this.fxView = view;
        randomStrategy = new RandomStrategy();
        fxView.setController(this);
        game.addObserver(this);
        setupEventHandlers();
    }

    private void setupEventHandlers() {
        // Gestionnaire pour Undo
        fxView.getUndoButton().setOnAction(e -> handleUndo());

        // Gestionnaire pour Redo
        fxView.getRedoButton().setOnAction(e -> handleRedo());

        // Gestionnaire pour Quit
        fxView.getGiveUpButton().setOnAction(e -> handleQuit());

        fxView.getPlayBot().setOnAction(e -> handlePlayBot());
    }

    private void handlePlayBot() {
        randomStrategy.play(fxView, game);
    }


    private void handleUndo() {
        try {
            game.undo();
            fxView.updateBoard(game); // Mettez à jour la vue après l'action
            FxView.setStatus("Dernier coup annulé !");
        } catch (OxonoExecption e) {
            FxView.setStatus("Impossible d'annuler : " + e.getMessage());
        }
    }

    private void handleRedo() {
        try {
            game.redo();
            fxView.updateBoard(game); // Mettez à jour la vue après l'action
            FxView.setStatus("Coup refait !");
        } catch (OxonoExecption e) {
            FxView.setStatus("Impossible de refaire : " + e.getMessage());
        }
    }

    private void handleQuit() {
        FxView.setStatus("Le joueur a abandonné la partie.");
        game.quitGame();
    }

    public void start() {
       fxView.showStartDialog(game);
    }

    public void handleClick(Position position) {
        try {
            Piece clickedPiece = game.getPiece(position);

            if (!isPlacingTotem) {
                if (clickedPiece instanceof Totem) {

                    clickTotemPos = position;
                    lastTotemPlay = (Totem) game.getPiece(clickTotemPos);

                    accessiblePositions = game.getFreeposTotem((Totem) clickedPiece);
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

    @Override
    public void update(ObservableEvent event) {
        switch (event) {
            case GAME_START:

            case PLACE_PAWN:
                fxView.updateBoard(game);

                fxView.updateRacks(game.getRemainingPawns());
                break;
            case UNDO:
            case MOVE_TOTEM:
                fxView.updateBoard(game);

                break;
            case WIN:
                fxView.gameWin(game);
                break;
            case QUIT:
                FxView.setStatus("Le jeu est terminé.");
                break;
        }
    }
}