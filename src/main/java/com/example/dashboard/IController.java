package com.example.dashboard;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;

public class IController {
    //Item  class to define item objects
    @FXML
    public TextField icName;
    public TextField icPrice;
    public TextField icX;
    public TextField icY;
    public TextField icLength;
    public TextField icWidth;
    public TextField icHeight;


    String name, price, x, y, length, width, height;

    //Create an item object
    Item item= new Item("", "", "", "", "", "", "");

    //All changes set object variables to what is typed in dialogue
    public void nameChange(KeyEvent event){
        name = icName.getText();
        item.setName(name);
    }
    public void priceChange(KeyEvent event){
        price = icPrice.getText();
        item.setPrice(price);
    }

    public void locationxChange(KeyEvent event){
        x = icX.getText();
        item.setLocationX(x);
    }

    public void locationyChange(KeyEvent event){
        y = icY.getText();
        item.setLocationY(y);
    }

    public void lengthChange(KeyEvent event){
        length = icLength.getText();
        item.setLength(length);
    }

    public void widthChange(KeyEvent event){
        width = icWidth.getText();
        item.setWidth(width);
    }

    public void heightChange(KeyEvent event){
        height = icHeight.getText();
        item.setHeight(height);
    }


    //Submit button is clicked
    public Object icClicked(ActionEvent event){

        System.out.println(item);
        return item.getName();
    }
}
