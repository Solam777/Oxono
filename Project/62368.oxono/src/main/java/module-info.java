module project._62368_xono {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;

    opens g62368_oxono.project to javafx.fxml;
    exports g62368_oxono.project;
}