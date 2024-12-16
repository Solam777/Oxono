package g62368_oxono.project.View;

import g62368_oxono.project.Controller.JeuFx;
import g62368_oxono.project.model.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Arrays;
import java.util.Optional;

/**
 * The FxView class represents the graphical user interface for the game.
 * It manages the layout and interaction of various UI components.
 */
public class FxView  {
    private BoardFx boardFx;
    private static VBox root;
    private VBox pinkPlayerRack;
    private VBox blackPlayerRack;
    private Button giveUpButton;
    private Button undoButton;
    private Button redoButton;
    private Label playerTurnLabel;
    private static Label statusLabel;
    private Button playBot;
    private GameAlert gameAlert;
    private int tailleTableau = 6;

    /**
     * Constructs the FxView and initializes the view with the specified board size.
     */
    public FxView() {
        createView(tailleTableau);
        root.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
    }

    /**
     * Sets the controller for the FxView.
     *
     * @param controller the JeuFx controller to be set
     */
    public void setController(JeuFx controller) {
        boardFx.setController(controller);
    }

    /**
     * Returns the root VBox of the view.
     *
     * @return the root VBox
     */
    public VBox getRoot() {
        return root;
    }

    /**
     * Returns the BoardFx instance.
     *
     * @return the BoardFx instance
     */
    public BoardFx getBoardFx() {
        return this.boardFx;
    }

    /**
     * Creates the view layout with the specified board size.
     *
     * @param tailleTableau the size of the board
     */
    public void createView(int tailleTableau) {
        root = new VBox(30);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.CENTER);

        VBox gameContainer = new VBox(20);
        gameContainer.setAlignment(Pos.CENTER);

        HBox header = Header();
        HBox boardGrid = new HBox();
        boardFx = new BoardFx(tailleTableau, 800, 600);
        boardGrid.setAlignment(Pos.CENTER);

        boardGrid.getChildren().add(boardFx.getGridPane());
        HBox playerRacks = createPlayerRacks();

        gameContainer.getChildren().addAll(header, boardGrid, playerRacks);
        root.getChildren().add(gameContainer);

        statusLabel = new Label();
        statusLabel.getStyleClass().add("statusGame");
        root.getChildren().add(statusLabel);
    }

    /**
     * Sets the status text in the status label.
     *
     * @param text the status text to be set
     */
    public static void setStatus(String text) {
        if (statusLabel != null) {
            statusLabel.setText(text);
        }
    }

    /**
     * Creates the header section of the view.
     *
     * @return the HBox containing the header
     */
    private HBox Header(){
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("header");

        playerTurnLabel = new Label("Tour actuel: Joueur Rose");
        playerTurnLabel.getStyleClass().add("player-turn-label");

        giveUpButton = new Button("Give up");
        undoButton = new Button("Retour");
        redoButton = new Button("Refaire");
        playBot = new Button("Playbot");

        for (Button btn : Arrays.asList(undoButton, redoButton, giveUpButton, playBot)) {
            btn.getStyleClass().add("game-button");
        }

        header.getChildren().addAll(redoButton, undoButton, giveUpButton, playBot, playerTurnLabel);
        return header;
    }

    /**
     * Creates a player rack section with the specified player name.
     *
     * @param playerName the name of the player
     * @return the VBox containing the player rack section
     */
    private VBox createPlayerRackSection(String playerName) {
        VBox section = new VBox(10);
        section.setAlignment(Pos.CENTER);
        // Limitez la largeur pour rendre les racks visuellement équilibrés
        section.setPrefWidth(200);
        section.setMaxWidth(200);

        Label nameLabel = new Label(playerName);
        nameLabel.getStyleClass().add("rack-label");

        section.getChildren().add(nameLabel);
        return section;
    }

    /**
     * Creates the player racks.
     *
     * @return the HBox containing the player racks
     */
    private HBox createPlayerRacks() {
        HBox racksContainer = new HBox(30);
        racksContainer.setAlignment(Pos.CENTER);
        racksContainer.getStyleClass().add("rack-container");

        pinkPlayerRack = createPlayerRackSection("Joueur Rose");
        blackPlayerRack = createPlayerRackSection("Joueur Noir");

        racksContainer.getChildren().addAll(pinkPlayerRack, blackPlayerRack);
        return racksContainer;
    }

    /**
     * Updates the player racks with the remaining pawns.
     *
     * @param remainingPawns an array containing the remaining pawns for each player
     */
    public void updateRacks(int[] remainingPawns) {
        updatePlayerRack(pinkPlayerRack, remainingPawns[0], remainingPawns[1], Color.PINK);
        updatePlayerRack(blackPlayerRack, remainingPawns[2], remainingPawns[3], Color.BLACK);
    }

    /**
     * Updates a player's rack with the specified number of pawns.
     *
     * @param rack the VBox representing the player's rack
     * @param xPawns the number of X pawns
     * @param oPawns the number of O pawns
     * @param color the color of the pawns
     */
    private void updatePlayerRack(VBox rack, int xPawns, int oPawns, Color color) {
        // Garde le label du joueur
        Node playerLabel = rack.getChildren().get(0);
        rack.getChildren().clear();
        rack.getChildren().add(playerLabel);

        // Crée la ligne des pions X
        VBox xSection = new VBox(5);
        xSection.setAlignment(Pos.CENTER);
        Label xLabel = new Label("Pions X");
        xLabel.getStyleClass().add("rack-label");

        HBox xPawnsBox = new HBox(5);
        xPawnsBox.setAlignment(Pos.CENTER);
        xPawnsBox.getStyleClass().add("pawns-container");

        for (int i = 0; i < xPawns; i++) {
            Pawn pawn = new Pawn(Mark.X, color);
            PawnFx pawnFX = new PawnFx(pawn.getMark(), color, 40);
            xPawnsBox.getChildren().add(pawnFX);
        }
        xSection.getChildren().addAll(xLabel, xPawnsBox);

        // Crée la ligne des pions O
        VBox oSection = new VBox(5);
        oSection.setAlignment(Pos.CENTER);
        Label oLabel = new Label("Pions O");
        oLabel.getStyleClass().add("rack-label");

        HBox oPawnsBox = new HBox(5);
        oPawnsBox.setAlignment(Pos.CENTER);
        oPawnsBox.getStyleClass().add("pawns-container");

        for (int i = 0; i < oPawns; i++) {
            Pawn pawn = new Pawn(Mark.O, color);
            PawnFx pawnFX = new PawnFx(pawn.getMark(), color, 40);
            oPawnsBox.getChildren().add(pawnFX);
        }
        oSection.getChildren().addAll(oLabel, oPawnsBox);

        rack.getChildren().addAll(xSection, oSection);
    }

    /**
     * Returns the play bot button.
     *
     * @return the play bot button
     */
    public Button getPlayBot() {
        return playBot;
    }

    /**
     * Returns the redo button.
     *
     * @return the redo button
     */
    public Button getRedoButton() {
        return redoButton;
    }

    /**
     * Returns the give up button.
     *
     * @return the give up button
     */
    public Button getGiveUpButton() {
        return giveUpButton;
    }

    /**
     * Returns the undo button.
     *
     * @return the undo button
     */
    public Button getUndoButton() {
        return undoButton;
    }

    /**
     * Updates the board with the current game state.
     *
     * @param game the current game instance
     */
    public void updateBoard(Game game) {
        boardFx.updateBoard(game);
        playerTurnLabel.setText("Tour actuel: Joueur " + (game.getCurrentPlayer().getColor() == Color.PINK ? "Rose" : "Noir"));
    }

    /**
     * Displays a game win alert.
     *
     * @param game the current game instance
     */
    public void gameWin(Game game) {
        gameAlert = new GameAlert(Alert.AlertType.INFORMATION);
        gameAlert.showAlertVictory(game);
    }

    public void gameDraw(Game game) {
        gameAlert = new GameAlert(Alert.AlertType.INFORMATION);
        gameAlert.showAlertDraw(game);
    }

    /**
     * The GameSettings class represents the settings for a new game.
     */
    private static class GameSettings {
        final int boardSize;
        final int gameMode;

        /**
         * Constructs the GameSettings with the specified board size and game mode.
         *
         * @param boardSize the size of the board
         * @param gameMode the mode of the game
         */
        GameSettings(int boardSize, int gameMode) {
            this.boardSize = boardSize;
            this.gameMode = gameMode;
        }
    }

    /**
     * Displays the start dialog for a new game.
     *
     * @param game the current game instance
     */
    public void showStartDialog(Game game) {
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
                "Humain vs Humain"
        );
        gameMode.setValue("Humain vs Humain/BOT");

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
            tailleTableau = settings.boardSize;
            game.initializeGame(tailleTableau);
            setStatus("La partie commence! C'est au tour du joueur " +
                    (game.getCurrentPlayer().getColor() == Color.PINK ? "Rose" : "Noir"));
        });
    }
}