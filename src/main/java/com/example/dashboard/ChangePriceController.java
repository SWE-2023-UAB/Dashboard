package com.example.dashboard;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class ChangePriceController {

    //Adding in the singleton
    DashApplication instance = DashApplication.getInstance();

    @FXML
    public TextField newPriceField;

    String newPrice;

    @FXML
    public void changePrice(KeyEvent e){
        newPrice = newPriceField.getText();
    }

    public String getNewPrice(){
        return newPrice;
    }
}
