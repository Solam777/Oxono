

package g62368_oxono.project.Controller;

import g62368_oxono.project.View.FxView;
import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import g62368_oxono.project.model.Observer.Observer;
import g62368_oxono.project.model.Strategy.Bot;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;

import java.util.List;
import java.util.Optional;

public class JeuFx implements Observer {
    private final FxView fxView;
    private Game game;


    private Position clickTotemPos;

    private boolean isPlacingTotem;
    private Totem lastTotemPlay;

    private List<Position> accessiblePositions;

    public JeuFx(Game game, FxView view) {
        this.game = game;
        this.fxView = view;

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
        List<Position> positionsValideTotem = game.getFreeposTotem(lastTotemPlay);
        List<Position> positionsValidePawn = game.getFreeposPawn(lastTotemPlay);

        int indexpawn= getRandom(0, positionsValidePawn.size());
        Position posPawn = positionsValidePawn.get(indexpawn);

        int indexTotem= getRandom(0, positionsValideTotem.size());
        Position posTotem = positionsValidePawn.get(indexTotem);

        int indexChoice = getRandom(0, 1);

        if(indexChoice==0){
            lastTotemPlay = new Totem(Mark.O);
        }
        else{
            lastTotemPlay = new Totem(Mark.X);
        }

        game.playTotem(posTotem,lastTotemPlay);
        game.playPawn( posPawn);
        fxView.updateBoard(game); // Mettez à jour la vue après l'action
    }

    public static int getRandom(int min, int max) {
        int range = (max - min) + 1;
        int random = (int) ((range * Math.random()) + min);
        return random;
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
        game.initializeGame(6);
    }

    public void handleClick(Position position) {
        try {
            Piece clickedPiece = game.getPiece(position);

            if (!isPlacingTotem) {
                if (clickedPiece instanceof Totem) {

                    clickTotemPos = position;
                    lastTotemPlay = (Totem) game.getPiece(clickTotemPos);

                    accessiblePositions = game.getFreeposTotem((Totem) clickedPiece);
                    fxView.getBoardFx().highlightAccessiblePlaces(accessiblePositions,(Totem)clickedPiece);
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

    // Ajout de la méthode clearHighlights
    private void clearSelectionAndHighlights() {
        clickTotemPos = null;
        if (fxView.getBoardFx() != null) {
            fxView.getBoardFx().clearHighlights();
        }
    }

    private static class GameSettings {
        final int boardSize;
        final int gameMode;

        GameSettings(int boardSize, int gameMode) {
            this.boardSize = boardSize;
            this.gameMode = gameMode;
        }
    }

    public void showStartDialog() {
        Dialog<GameSettings> dialog = new Dialog<>();
        dialog.setTitle("Nouvelle Partie");
        dialog.setHeaderText("Choisissez les paramètres de jeu");

        ButtonType startButtonType = new ButtonType("Commencer", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(startButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new javafx.geometry.Insets(20, 150, 10, 10));

        ComboBox<Integer> boardSize = new ComboBox<>();
        for (int i = 6; i <= 12; i++) boardSize.getItems().add(i);
        boardSize.setValue(6);

        ComboBox<String> gameMode = new ComboBox<>();
        gameMode.getItems().addAll(
                "Humain vs Humain",
                "Humain vs IA Aléatoire"
        );
        gameMode.setValue("Humain vs Humain");

        grid.add(new Label("Taille du plateau:"), 0, 0);
        grid.add(boardSize, 1, 0);
        grid.add(new Label("Mode de jeu:"), 0, 1);
        grid.add(gameMode, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == startButtonType) {
                return new GameSettings(boardSize.getValue(), gameMode.getSelectionModel().getSelectedIndex() + 1);
            }
            return null;
        });

        Optional<GameSettings> result = dialog.showAndWait();
        result.ifPresent(settings -> {
            game.initializeGame(settings.boardSize);
            fxView.setStatus("La partie commence! C'est au tour du joueur " +
                    (game.getCurrentPlayer().getColor() == Color.PINK ? "Rose" : "Noir"));

        });
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
                clearSelectionAndHighlights();
                break;
            case WIN:
                FxView.setStatus("Le joueur " + game.getCurrentPlayer() + " a gagné!");
                //System.exit(0);
                break;
            case DRAW:
                FxView.setStatus("Match nul!");
                break;
            case QUIT:
                FxView.setStatus("Le jeu est terminé.");
                break;
        }
    }
}