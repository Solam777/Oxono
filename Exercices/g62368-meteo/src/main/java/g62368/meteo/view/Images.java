package g62368.meteo.view;

public class Images {
    public static String getImage(int weatherCode) {
        String url = "image/";
        return switch (weatherCode) {

            case 1 -> url + "sunny.png";
            case 2 -> url + "cloudy.png";
            case 3 -> url + "rainy.png";
            default -> url + "sunny.peg";
        };
    }
}
