package com.example.dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.stage.Stage;

import java.io.IOException;

//Override method for launch
public class DashApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        // Load main dashboard
        FXMLLoader fxmlLoader = new FXMLLoader(DashApplication.class.getResource("dash-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1280, 1024);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.show();
    }

    //Main Method
    public static void main(String[] args) {
        launch();
    }
}