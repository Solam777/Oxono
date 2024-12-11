package g62368_oxono.project.View;

import g62368_oxono.project.Controller.JeuFx;
import g62368_oxono.project.model.*;
import g62368_oxono.project.model.Observer.ObservableEvent;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.List;


public class BoardFx extends StackPane {
    private final int boardSize;
    private final GridPane gridPane;

    public int getBoardSize() {
        return boardSize;
    }

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
    }

    public void setController(JeuFx controller) {
        this.controllerFx = controller;
    }

    public void updateBoard(Game game) {

        gridPane.getChildren().clear();
        for (int row = 0; row < boardSize; row++) {
            for (int col = 0; col < boardSize; col++) {
                Position position = new Position(col, row);
                StackPane cell = cells.createCell();
                cell.getStyleClass().add("board-cell");

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
    public void highlightAccessiblePlaces(List<Position> positions, Totem totem) {
        // Réinitialiser toutes les surbrillances
        clearHighlights();

        // Mettre en évidence les positions accessibles
        for (Position position : positions) {
            StackPane cell = getCellAt(position.x(), position.y());
            if (cell != null) {
                // Toutes les positions dans la liste sont valides pour le totem
                cell.getStyleClass().add("highlight-totem-cell");

                // Ajouter des effets de survol
                cell.setOnMouseEntered(e -> {
                    cell.getStyleClass().add("highlight-hover");
                });

                cell.setOnMouseExited(e -> {
                    cell.getStyleClass().remove("highlight-hover");
                    cell.getStyleClass().add("highlight-totem-cell");
                });
            }
        }
    }

    // Méthode auxiliaire pour réinitialiser les surbrillances
    public void clearHighlights() {
        gridPane.getChildren().forEach(node -> {
            if (node instanceof StackPane) {
                StackPane cell = (StackPane) node;
                cell.getStyleClass().removeAll(
                        "highlight-totem-cell",
                        "highlight-hover"
                );
                // Réinitialiser les gestionnaires d'événements
                cell.setOnMouseEntered(null);
                cell.setOnMouseExited(null);
            }
        });
    }

    private StackPane getCellAt(int col, int row) {
        for (javafx.scene.Node node : gridPane.getChildren()) {
            Integer nodeCol = GridPane.getColumnIndex(node);
            Integer nodeRow = GridPane.getRowIndex(node);

            // Gérer le cas où les indices sont null (première cellule)
            if ((nodeCol == null ? 0 : nodeCol) == col &&
                    (nodeRow == null ? 0 : nodeRow) == row) {
                return (StackPane) node;
            }
        }
        return null;
    }

    public GridPane getGridPane() {
        return gridPane;
    }

//    public void resizeGrid(int newSize) {
//        this.size = newSize;
//        gridPane.getChildren().clear(); // Efface l'ancienne grille
//        createGrid(); // Recrée la grille avec la nouvelle taille
//    }

}
