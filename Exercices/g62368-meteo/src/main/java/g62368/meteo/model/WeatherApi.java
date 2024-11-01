package g62368.meteo.model;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDate;

public class WeatherApi {
    static ObjectMapper objectMapper = new ObjectMapper();
    static WeatherObject fetch (String address, LocalDate date) throws WeatherException{
        try {
            double[] coordination = getadress(address);
            String weatherUrl = getString(address, date, coordination);

            HttpClient client = HttpClient.newHttpClient();
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create(weatherUrl))
                    .GET()
                    .build();
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            JsonNode jsonNode = objectMapper.readTree(response.body());
            JsonNode daily = jsonNode.get("daily");
            int weatherCode = daily.get("weather_code").get(0).asInt();
            double tempMax = daily.get("temperature_2m_max").get(0).asDouble();
            double tempMin = daily.get("temperature_2m_min").get(0).asDouble();
            return new WeatherObject(address, date, weatherCode, tempMin, tempMax);

        } catch (IOException | InterruptedException e) {
            throw new WeatherException("error city not find");
        }


    }

    private static String getString(String address, LocalDate date, double[] coordination) throws WeatherException {
        if (coordination == null) {
            throw new WeatherException("Ville introuvable : " + address);
        }
        double latitude = coordination[0];
        double longitude = coordination[1];
        String weatherUrl = "https://api.open-meteo.com/v1/forecast?latitude=" + latitude +
                "&longitude=" + longitude + "&daily=weather_code,temperature_2m_max,temperature_2m_min" +
                "&timezone=Europe%2FBerlin&start_date=" + date + "&end_date=" + date;
        return weatherUrl;
    }

    public static double[] getadress(String address) throws IOException, InterruptedException {
        String pos ="https://nominatim.openstreetmap.org/search.php?q=" + address + "&format=jsonv2";
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(pos))
                .GET()
                .build();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
        JsonNode jsonArray = objectMapper.readTree(response.body());
        if (jsonArray.size() <= 0) {
            return null;
        }
        JsonNode location = jsonArray.get(0);
        double latitude = location.get("lat").asDouble();
        double longitude = location.get("lon").asDouble();
        return new double[]{latitude, longitude};


    }
}
