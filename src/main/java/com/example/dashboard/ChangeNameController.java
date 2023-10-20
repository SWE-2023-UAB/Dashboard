package com.example.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

//servers the purpose of being the controller for modifying name in change-name.fxml
public class ChangeNameController {
    //used for changing item/container names

    @FXML
    public TextField newNameField;
    @FXML
    public Button submitNewName;
    private String newName;

    @FXML
    public void changeName(KeyEvent e){
        //placeholder of current name
        newName = newNameField.getText();
    }

    public String getNewName(){
        return newName;
    }
}
