package g62368.meteo.model;

import java.time.LocalDate;


public record WeatherObject(String locality, LocalDate Date, int weatherCode, double tempMin , double tempMax) {
}
