package g62368_oxono.project.View;

import javafx.scene.image.Image;

public class ImageFx {

    public static Image getImagePath(String fileName) {
        String path = "/images/";
        String fullPath = path + switch (fileName) {
            case "Background" -> "box-background.png";
            case "CenterBox" -> "center-box-background.png";
            case "TotemX" -> "totemX.PNG";
            case "TotemO" -> "totemO.PNG";
            case "PawnBlackX" -> "pawnBlackX.png";
            case "PawnBlackO" -> "pawnBlackO.png";
            case "PawnPinkX" -> "pawnPinkX.png";
            case "PawnPinkO" -> "pawnPinkO.png";
            default -> throw new IllegalArgumentException("Invalid image file name: " + fileName);
        };

        // Chargement de l'image à partir des ressources
        var resource = ImageFx.class.getResource(fullPath);
        if (resource == null) {
            throw new IllegalArgumentException("Resource not found: " + fullPath);
        }
        return new Image(resource.toExternalForm());
    }
}
