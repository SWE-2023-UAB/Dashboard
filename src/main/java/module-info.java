module com.example.dashboard {
    requires javafx.controls;
    requires javafx.fxml;

    requires com.dlsc.formsfx;
    requires java.desktop;

    opens com.example.dashboard to javafx.fxml;
    exports com.example.dashboard;
}