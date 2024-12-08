package g62368_oxono.project.View;

import g62368_oxono.project.Controller.JeuFx;
import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;


public class BoardFx extends StackPane {
    private final int boardSize;
    private final GridPane gridPane;

    public CellFx getCells() {
        return cells;
    }

    public final double cellSize; // Taille des cases
    private final CellFx cells; // Correction : final pour une meilleure structure
    private JeuFx controllerFx;


    public BoardFx(int boardSize, double boardWidth, double boardHeight) {
        this.boardSize = boardSize;
        this.cellSize = Math.min(boardWidth, boardHeight) / boardSize; // Taille d'une cellule
        this.gridPane = new GridPane();
        this.gridPane.setAlignment(Pos.CENTER);
        this.cells = new CellFx();


        // Définir le fond
////        ImageView background = new ImageView(ImageFx.getImagePath("Background"));
//        background.setFitWidth(boardWidth);
//        background.setFitHeight(boardHeight);
//        this.getChildren().add(background);
    }

    public void setController(JeuFx controller) {
        this.controllerFx = controller;
    }

//    public void buildGrid() {
//        for (int row = 0; row < boardSize; row++) {
//            for (int col = 0; col < boardSize; col++) {
//                CellFx cell = new CellFx();
//                cells[row][col] = cell; // Stocke la cellule pour référence
//                Position position = new Position(col, row);
//                Piece piece = game.getPiece(position);
//
//                if (piece != null) {
//                    if (piece instanceof Totem) {
//                        TotemFx totemFx = new TotemFx(piece.getMark(), cellSize);
//                        cell.getChildren().add(totemFx); // Ajoute visuellement le totem
//                    } else if (piece instanceof Pawn) {
//                        PawnFx pawnFx = new PawnFx(piece.getMark(), ((Pawn) piece).getColor(), cellSize);
//                        cell.getChildren().add(pawnFx); // Ajoute visuellement le pion
//                    }
//                }
//
//                cell.setOnMouseClicked(event -> {
//                    if (controllerFx != null) {
//                        controllerFx.handleClick(position);
//                    }
//                });
//                gridPane.add(cell, col, row);
//            }
//        }
//    }

    public void updateBoard(Game game) {

        gridPane.getChildren().clear();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Position position = new Position(col, row);
                StackPane cell = cells.createCell();

                // Récupère la pièce à cette position
                Piece piece = game.getPiece(position);
                if (piece != null) {
                    if (piece instanceof Totem) {
                        TotemFx totemFx = new TotemFx(piece.getMark(), 100);
                        cell.getChildren().add(totemFx); // Ajoute visuellement le totem
                    } else if (piece instanceof Pawn) {
                        PawnFx pawnFx = new PawnFx(piece.getMark(), ((Pawn) piece).getColor(), 100);
                        cell.getChildren().add(pawnFx); // Ajoute visuellement le pion
                    }
                }
                cell.setOnMouseClicked(event -> {
                   if (controllerFx != null) {
                        controllerFx.handleClick(position);
                    }
                });
                gridPane.add(cell, col, row);

            }
        }
    }

    public double getCellSize() {
        return cellSize;
    }

    public GridPane getGridPane() {
        return gridPane;
    }
}
