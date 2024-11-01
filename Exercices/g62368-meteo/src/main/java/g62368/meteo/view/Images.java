package g62368.meteo.view;

public class Images {
    public static String getImage(int weatherCode) {
        return switch (weatherCode) {
            case 1 -> "/image/sunny.png";
            case 2 -> "/image/cloudy.png";
            case 3 -> "/image/rainy.png";
            default -> "/image/default.png";
        };
    }
}
