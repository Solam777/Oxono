package org.example.g62368meteo;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class HelloApplication extends Application {

    public static void main(String[] args) {
        launch();
    }

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void start(Stage stage) {

        stage.setTitle("weather");

        BorderPane root = new BorderPane();
        Button actionButton = new Button("get weather");
        DatePicker datePicker = new DatePicker();
        TextField textfield = new TextField();
        Label resultLabel = new Label("set a city");

        actionButton.setOnAction(e -> actionButton(textfield.getText(), datePicker.getValue(), resultLabel));
        root.setTop(textfield);
        root.setCenter(datePicker);
        root.setBottom(actionButton);
        root.setRight(resultLabel);
        Scene scene = new Scene(root, 400, 400);
        stage.setScene(scene);
        stage.show();
    }

    private void actionButton(String city, LocalDate date, Label resultLabel) {
        if (city == null || city.isEmpty() || date == null) {
            showAlert("error", "seat a city and date");
            return;
        }

        try {

            double[] coordinates = getCoordinates(city);
            if (coordinates == null) {
                showAlert("error", "city not find");
                return;
            }
            getWeather(coordinates[0], coordinates[1], date, resultLabel);
        } catch (Exception e) {
            showAlert("error", "error ");
            e.printStackTrace();
        }
    }

    private double[] getCoordinates(String city) throws IOException, InterruptedException {
        String url = "https://nominatim.openstreetmap.org/search.php?q=" + city + "&format=jsonv2";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode jsonArray = objectMapper.readTree(response.body());
        if (!jsonArray.isEmpty()) {
            JsonNode location = jsonArray.get(0);
            double latitude = location.get("lat").asDouble();
            double longitude = location.get("lon").asDouble();
            return new double[]{latitude, longitude};
        }
        return null;
    }

    private void getWeather(double latitude, double longitude, LocalDate date, Label resultLabel) throws IOException, InterruptedException {
        String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                "&longitude=" + longitude + "&daily=weather_code,temperature_2m_max,temperature_2m_min," +
                "precipitation_sum,rain_sum,sunrise,sunset&timezone=Europe%2FBerlin&start_date=" + date + "&end_date=" + date;

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(weatherUrl))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        JsonNode jsonNode = objectMapper.readTree(response.body());
        JsonNode daily = jsonNode.get("daily");

        double tempMax = daily.get("temperature_2m_max").get(0).asDouble();
        double tempMin = daily.get("temperature_2m_min").get(0).asDouble();
        double precipitationSum = daily.get("precipitation_sum").get(0).asDouble();
        double rainSum = daily.get("rain_sum").get(0).asDouble();
        String sunrise = daily.get("sunrise").get(0).asText();
        String sunset = daily.get("sunset").get(0).asText();


        String weatherInfo = "max temp: " + tempMax + "°C\n" +
                "min temp : " + tempMin + "°C\n" +
                "précipitations : " + precipitationSum + " mm\n" +
                "rain : " + rainSum + " mm\n" +
                "sunrise : " + sunrise + "\n" +
                "sunset : " + sunset;

        resultLabel.setText(weatherInfo);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
