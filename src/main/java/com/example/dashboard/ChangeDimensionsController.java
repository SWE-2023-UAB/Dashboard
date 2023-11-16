package com.example.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

//servers the purpose of being the controller for modifying dimensions in change-dimensions.fxml
public class ChangeDimensionsController {

    //Adding in the singleton
    DashApplication instance = DashApplication.getInstance();
    @FXML
    public TextField lengthField;
    @FXML
    public TextField widthField;
    @FXML
    public TextField heightField;

    String newLength, newWidth, newHeight;

    @FXML
    public void changeLength(KeyEvent e){
        newLength = lengthField.getText();
    }

    @FXML
    public void changeWidth(KeyEvent e){
        newWidth = widthField.getText();
    }

    @FXML
    public void changeHeight(KeyEvent e){
        newHeight = heightField.getText();
    }

    public String getNewLength(){
        return newLength;
    }

    public String getNewWidth(){
        return newWidth;
    }

    public String getNewHeight(){
        return newHeight;
    }
}
