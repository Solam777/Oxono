
package g62368_oxono.project.View;

import g62368_oxono.project.model.Mark;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class TotemFx extends Rectangle {

    public TotemFx(Mark mark, double cellSize) {
        super(cellSize, cellSize); // Fixe la taille du totem en fonction de la case

        // Définir l'image en fonction du type de totem (X ou O)
        if (mark == Mark.X) {
            setFill(new ImagePattern(ImageFx.getImagePath("TotemX")));
        } else if (mark == Mark.O) {
            setFill(new ImagePattern(ImageFx.getImagePath("TotemO")));
        }


        setScaleX(0.8);
        setScaleY(0.8);
    }
}
