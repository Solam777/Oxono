package g62368.meteo.controller;

import g62368.meteo.model.Model;
import g62368.meteo.model.WeatherObject;
import g62368.meteo.view.MainView;

import java.time.LocalDate;

public class Controller {
    Model model;
    MainView mainView;

    public Controller(Model model, MainView mainView) {
        this.model = model;
        this.mainView = mainView;
    }

    public void actionFetch(String address, LocalDate date) {
        try {
            WeatherObject weather = model.fetch(address, date);
            mainView.update(weather);
        } catch (Exception e) {
            mainView.showError("c'ant get the weather's info: " + e.getMessage());
        }
    }
}
