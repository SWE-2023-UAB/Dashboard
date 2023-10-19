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

    public Button icButton;

    String name, price, x, y, length, width, height;

    //Create an item object to store new item container
    ItemContainer itemC = new ItemContainer("", "", "", "", "", "", "");

    //All changes set object variables to what is typed in dialogue
    public void nameChange(KeyEvent event){
        name = icName.getText();
        itemC.setName(name);
    }
    public void priceChange(KeyEvent event){
        price = icPrice.getText();
        itemC.setPrice(price);
    }

    public void locationxChange(KeyEvent event){
        x = icX.getText();
        itemC.setLocationX(x);
    }

    public void locationyChange(KeyEvent event){
        y = icY.getText();
        itemC.setLocationY(y);
    }

    public void lengthChange(KeyEvent event){
        length = icLength.getText();
        itemC.setLenght(length);
    }

    public void widthChange(KeyEvent event){
        width = icWidth.getText();
        itemC.setWidth(width);
    }

    public void heightChange(KeyEvent event){
        height = icHeight.getText();
        itemC.setHeight(height);
    }


    //Submit button is clicked
    public Object icClicked(ActionEvent event){

        System.out.println(itemC);
        return itemC.name;
    }
}
