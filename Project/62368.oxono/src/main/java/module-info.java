module project._62368_xono {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.desktop;

    opens g62368_oxono.project to javafx.fxml;
    exports g62368_oxono.project.View;
    exports g62368_oxono.project;
}