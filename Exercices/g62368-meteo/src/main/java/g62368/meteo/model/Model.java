package g62368.meteo.model;

import java.time.LocalDate;
import com.fasterxml.jackson.databind.ObjectMapper;

public class Model {
    private String adress;
    private LocalDate date;
    ObjectMapper objectMapper  = new ObjectMapper();

    public Model() {
        this.date = date;
        this.adress = adress;
    }

    public WeatherObject fetch(String adress, LocalDate date) throws WeatherException {
        return WeatherApi.fetch(adress,date);
    }

}
