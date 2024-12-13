module com.example.demo {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires javafx.media;
    requires java.base;
    requires java.desktop;

    exports com.example.demo;
    exports com.example.demo.controller;

    opens com.example.demo to javafx.fxml;
}
