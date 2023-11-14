package com.example.dashboard;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

public class CustomTitleBar extends HBox {
    private Button closeButton;
    private Button minimizeButton;
    private double xOffset = 0;
    private double yOffset = 0;

    public CustomTitleBar(Stage stage) {
        closeButton = new Button();
        closeButton.setPrefWidth(15);
        closeButton.setPrefHeight(15);
        closeButton.setStyle("-fx-background-color: transparent;");
        closeButton.setOnAction(e -> stage.close());
        closeButton.setPadding(new Insets(5, 10, 5, 0));

        minimizeButton = new Button();
        minimizeButton.setPrefWidth(15);
        minimizeButton.setPrefHeight(15);
        minimizeButton.setStyle("-fx-background-color: transparent;");
        minimizeButton.setOnAction(e -> stage.setIconified(true));
        minimizeButton.setPadding(new Insets(5, 5, 5, 5));
        HBox buttonBox = new HBox();
        buttonBox.setAlignment(Pos.CENTER_RIGHT);
        buttonBox.getChildren().addAll(minimizeButton, closeButton);
        HBox.setHgrow(buttonBox, Priority.ALWAYS);

        this.getChildren().add(buttonBox);

        this.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        this.setOnMouseDragged(event -> {
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        Image closeImage = new Image("https://uxwing.com/wp-content/themes/uxwing/download/signs-and-symbols/red-circle-icon.png");
        ImageView closeImageView = new ImageView(closeImage);
        closeImageView.setFitWidth(15);
        closeImageView.setFitHeight(15);
        closeImageView.setPreserveRatio(true);
        closeButton.setGraphic(closeImageView);
        closeButton.setAlignment(Pos.CENTER);
        ColorAdjust darken = new ColorAdjust();
        darken.setBrightness(-0.5);
        closeButton.setOnMouseEntered(e -> closeImageView.setEffect(darken));
        closeButton.setOnMouseExited(e -> closeImageView.setEffect(null));

        Image minimizeImage = new Image("https://www.clker.com/cliparts/8/M/g/8/1/f/glossy-home-icon-button-orange-md.png");
        ImageView minimizeImageView = new ImageView(minimizeImage);
        minimizeImageView.setFitWidth(15);
        minimizeImageView.setFitHeight(15);
        minimizeImageView.setPreserveRatio(true);
        minimizeButton.setGraphic(minimizeImageView);
        minimizeButton.setAlignment(Pos.CENTER);
        minimizeButton.setOnMouseEntered(e -> minimizeImageView.setEffect(darken));
        minimizeButton.setOnMouseExited(e -> minimizeImageView.setEffect(null));
    }
}