package g62368_oxono.project.View;

import g62368_oxono.project.Controller.JeuFx;
import g62368_oxono.project.model.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;


import java.awt.event.ActionEvent;
import java.util.Arrays;

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


    public FxView() {
        createView();
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

    public void createView() {
        root = new VBox(30);
        root.setPadding(new Insets(25));
        root.setAlignment(Pos.CENTER);

        VBox gameContainer = new VBox(20);
        gameContainer.setAlignment(Pos.CENTER);

        HBox header = Header();
        HBox boardGrid = new HBox();
        boardFx = new BoardFx(6, 800, 600);
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
//    private void clickShow(ActionEvent event) {
//        Stage stage = new Stage();
//        Parent root = FXMLLoader.load(
//                FxView.class.getResource("YourClass.fxml"));
//        stage.setScene(new Scene(root));
//        stage.setTitle("My modal window");
//        stage.initModality(Modality.WINDOW_MODAL);
//        stage.initOwner(
//                ((Node)event.getSource()).getScene().getWindow() );
//        stage.show();
//    }
}
