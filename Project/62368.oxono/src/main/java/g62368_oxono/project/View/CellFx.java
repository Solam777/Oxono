package g62368_oxono.project.View;

import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

public class CellFx extends StackPane {

    public CellFx() {

    }

//    private void handleMouseClick(MouseEvent event) {
//        if (event.getButton() == MouseButton.PRIMARY) {
//            System.out.println(" position: (" + col + ", " + row + ")");
//        }
//    }
    public StackPane createCell(){
        // Définir la taille de la cellule
        StackPane cell = new StackPane();
        cell.setPrefSize(100, 100); // Ajuste la taille en fonction de tes besoins
        // Ajouter un style ou une bordure pour mieux visualiser la grille
        cell.setStyle("-fx-border-color: black; -fx-background-color: #009dff;");
        return cell;
    }
}
