package g62368_oxono.project.View;

import g62368_oxono.project.Controller.JeuFx;
import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import java.util.Arrays;
import java.util.Optional;

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
   private  GameAlert gameAlert;
   private int tailleTableau = 6;

    public FxView() {
        createView(tailleTableau);
        root.getStylesheets().add(getClass().getResource("/style/style.css").toExternalForm());
    }

    public void setController(JeuFx controller) {
        boardFx.setController(controller);
    }

    public VBox getRoot() {
        return root;
    }

    public BoardFx getBoardFx() {
        return this.boardFx;
    }

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

    public static void setStatus(String text) {
        if (statusLabel != null) {
            statusLabel.setText(text);
        }
    }

    /*l'en tete */
    private HBox Header(){
        
        HBox header = new HBox();
        header.setAlignment(Pos.CENTER);
        header.getStyleClass().add("header");

        playerTurnLabel = new Label("Tour actuel: Joueur Rose");
        playerTurnLabel.getStyleClass().add("player-turn-label");

         giveUpButton = new Button("Give up");
         undoButton = new Button("Retour");
         redoButton = new Button("Refaire");
         playBot =  new Button("Playbot");

        for (Button btn : Arrays.asList(undoButton, redoButton, giveUpButton,playBot)) {
            btn.getStyleClass().add("game-button");
        }

        header.getChildren().addAll(redoButton, undoButton, giveUpButton,playBot,playerTurnLabel);
        return header;
    }

    /**
     * Crée une section de rack pour un joueur
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
     * Crée les racks des joueurs
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
     * Met à jour les racks des joueurs
     */
    public void updateRacks(int[] remainingPawns) {
        updatePlayerRack(pinkPlayerRack, remainingPawns[0], remainingPawns[1], Color.PINK);
        updatePlayerRack(blackPlayerRack, remainingPawns[2], remainingPawns[3], Color.BLACK);

    }

    /**
     * Met à jour le rack d'un joueur
     */
    private void updatePlayerRack(VBox rack, int xPawns, int oPawns,Color color) {
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
            Pawn pawn = new Pawn(Mark.X,color);
            PawnFx pawnFX = new PawnFx(pawn.getMark(),color, 40);
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
            Pawn pawn = new Pawn(Mark.O,color);
            PawnFx pawnFX = new PawnFx(pawn.getMark(),color,40);
            oPawnsBox.getChildren().add(pawnFX);
        }
        oSection.getChildren().addAll(oLabel, oPawnsBox);

        rack.getChildren().addAll(xSection, oSection);
    }

    public Button getPlayBot() {
        return playBot;
    }

    public Button getRedoButton() {
        return redoButton;
    }

    public Button getGiveUpButton() {
        return giveUpButton;
    }

    public Button getUndoButton() {
        return undoButton;
    }

    public void updateBoard(Game game) {
        boardFx.updateBoard(game);
        playerTurnLabel.setText("Tour actuel: Joueur " + (game.getCurrentPlayer().getColor() == Color.PINK ? "Rose" : "Noir"));
    }

    public void gameWin(Game game) {
        gameAlert = new GameAlert(Alert.AlertType.INFORMATION);
        gameAlert.showAlert(game);
    }

    private static class GameSettings {
        final int boardSize;
        final int gameMode;

        GameSettings(int boardSize, int gameMode) {
            this.boardSize = boardSize;
            this.gameMode = gameMode;
        }
    }

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
            tailleTableau = settings.boardSize;
            System.out.println(tailleTableau);
//            createView(tailleTableau);
            game.initializeGame(tailleTableau);
            setStatus("La partie commence! C'est au tour du joueur " +
                    (game.getCurrentPlayer().getColor() == Color.PINK ? "Rose" : "Noir"));

        });
    }
}
