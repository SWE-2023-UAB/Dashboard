package com.example.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

//servers the purpose of being the controller for modifying location in change-location.fxml
public class ChangeLocationController {
    @FXML
    public TextField xCoordField;
    @FXML
    public TextField yCoordField;

    String newXCoord, newYCoord;

    @FXML
    public void changeXCoord(KeyEvent e){
        newXCoord = xCoordField.getText();
    }

    @FXML
    public void changeYCoord(KeyEvent e){
        newYCoord = yCoordField.getText();
    }

    public String getNewXCoord(){
        return newXCoord;
    }

    public String getNewYCoord(){
        return newYCoord;
    }
}
