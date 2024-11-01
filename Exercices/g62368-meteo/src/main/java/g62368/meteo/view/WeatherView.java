package g62368.meteo.view;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

public class WeatherView {
    private String name;
    private Label nameLabel;
    private Label tempMinLabel;
    private Label tempMaxLabel;
    private ImageView weatherImage;

    public WeatherView() {
        // Initialize the Labels and ImageView
        this.nameLabel = new Label();
        this.tempMinLabel = new Label();
        this.tempMaxLabel = new Label();
        this.weatherImage = new ImageView();
    }

    public void setName(String name) {
        this.name = name;
        nameLabel.setText(name);
    }

    public void setTempMin(double tempMin) {
        tempMinLabel.setText("Température min : " + tempMin + "°C");
    }

    public void setTempMax(double tempMax) {
        tempMaxLabel.setText("Température max : " + tempMax + "°C");
    }

    public void setImage(int weatherCode) {
        // Placeholder code for setting an image based on weatherCode
        // For example, you could load an image based on weatherCode
         this.weatherImage.setImage(new Image("path/to/image" + weatherCode + ".png"));
    }

    public VBox getView() {
        // Add the initialized components to the VBox
        return new VBox(10, nameLabel, tempMinLabel, tempMaxLabel);
    }
}
