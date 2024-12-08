
package g62368_oxono.project.View;


import g62368_oxono.project.model.Color;
import g62368_oxono.project.model.Mark;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;



public class PawnFx extends Rectangle {

    public PawnFx(Mark mark, Color color, double cellSize) {
        super(cellSize, cellSize);

        if (mark == Mark.X) {
            if (color == Color.PINK) {
                setFill(new ImagePattern(ImageFx.getImagePath("PawnPinkX")));
                setScaleX(2);
                setScaleY(2);

            } else if (color == Color.BLACK) {
                setFill(new ImagePattern(ImageFx.getImagePath("PawnBlackX")));
                setScaleX(2);
                setScaleY(2);
                setHeight(35);
                setWidth(33);

            }
        } else if (mark == Mark.O) {
            if (color == Color.PINK) {
                setFill(new ImagePattern(ImageFx.getImagePath("PawnPinkO")));
                setScaleX(2);
                setScaleY(2);
                setHeight(35);
                setWidth(33);

            } else if (color == Color.BLACK) {
                setFill(new ImagePattern(ImageFx.getImagePath("PawnBlackO")));
                setScaleX(2);
                setScaleY(2);
                setHeight(35);
                setWidth(33);

            }

        }
    }
}