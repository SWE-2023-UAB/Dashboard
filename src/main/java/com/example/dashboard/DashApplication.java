package com.example.dashboard;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

import java.io.IOException;


public class DashApplication extends Application {
    private static DashApplication instance;

    public DashApplication() {}

    public static synchronized DashApplication getInstance() {
        if (instance == null) {
            instance = new DashApplication();
        }
        return instance;
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(DashApplication.class.getResource("dash-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1098, 630);
        stage.setTitle("Dashboard");
        stage.setScene(scene);
        stage.initStyle(javafx.stage.StageStyle.UNDECORATED);
        CustomTitleBar customTitleBar = new CustomTitleBar(stage);
        AnchorPane anchorPane = (AnchorPane) scene.lookup("#title");
        anchorPane.getChildren().add(customTitleBar);
        AnchorPane.setTopAnchor(customTitleBar, 0.0);
        AnchorPane.setLeftAnchor(customTitleBar, 0.0);
        AnchorPane.setRightAnchor(customTitleBar, 0.0);
        AnchorPane.setBottomAnchor(customTitleBar, 0.0);
        stage.show();
        stage.setResizable(false);
    }
    // Main Method
    public static void main(String[] args) {
        getInstance().launchApp(args);
    }

    private void launchApp(String[] args) {
        launch(args);
    }
}