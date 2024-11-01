module org.example.g62368meteo {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.net.http;
    requires com.fasterxml.jackson.databind;
    requires java.desktop;

    opens org.example.g62368meteo to javafx.fxml;
    exports org.example.g62368meteo;
    exports g62368.meteo;

}