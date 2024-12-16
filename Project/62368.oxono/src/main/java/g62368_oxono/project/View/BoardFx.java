package g62368_oxono.project.View;

import g62368_oxono.project.Controller.JeuFx;
import g62368_oxono.project.model.*;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import java.util.List;


public class BoardFx extends StackPane {
    private final int boardSize;
    private final GridPane gridPane;

    public final double cellSize; // Taille des cases
    private final CellFx cells; // Correction : final pour une meilleure structure
    private JeuFx controllerFx;

    /**
     * Constructs a {@code BoardFx} with the specified dimensions and board size.
     *
     * @param boardSize   the number of rows and columns on the board
     * @param boardWidth  the width of the board
     * @param boardHeight the height of the board
     */
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

    /**
     * Updates the visual representation of the board based on the current game state.
     *
     * @param game the current state of the game
     */
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

    /**
     * Highlights the cells corresponding to accessible positions on the board.
     *
     * @param positions the list of positions to highlight
     */
    public void highlightAccessiblePlaces(List<Position> positions) {
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

    /**
     * Clears all highlighted cells on the board.
     */
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

    /**
     * Retrieves the cell at the specified column and row.
     *
     * @param col the column index of the cell
     * @param row the row index of the cell
     * @return the {@code StackPane} representing the cell, or {@code null} if no cell is found
     */
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

    /**
     * Retrieves the {@code GridPane} containing the board's layout.
     *
     * @return the {@code GridPane} representing the board
     */
    public GridPane getGridPane() {
        return gridPane;
    }

}
