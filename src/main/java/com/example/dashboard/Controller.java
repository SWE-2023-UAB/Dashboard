package com.example.dashboard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;

import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.Node;


public class Controller implements Initializable {
    public TreeView treeView;
    @FXML
    public void SelectItem(){

    }

    @FXML
    // Create Selection for Item Container Actions
    public ChoiceBox<String> itemContainerBox;
    public String[] itemContainerOptions = {
            "Add Item-Container",
            "Add Item",
            "Delete Item-Container",
            "Change name", "Change Price",
            "Change Location-X",
            "Change Location-Y",
            "Change Length",
            "Change Width", "Change Height"
    };


    @FXML
    private Label welcomeText;

    @Override
    //Set Item Container Actions and Allow Selections to be Selected
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemContainerBox.getItems().addAll(itemContainerOptions);
        itemContainerBox.setOnAction(this::GetContainerSelection);
    }
    //Get input from Item Container Actions
    public void GetContainerSelection (ActionEvent event){
        String containerSelection = itemContainerBox.getValue();
        if (containerSelection.equals("Add Item-Container")){
            //Make Dialog Pop Up
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("item-container.fxml"));
                DialogPane icDialogue = fxmlLoader.load();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(icDialogue);
                dialog.setTitle("Item Container");
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                if(clickedButton.get() == ButtonType.FINISH){
                    System.out.println("Finished Pressed");
                }
            } catch (IOException e) {
                // Handle the IOException, e.g., by printing an error message
                e.printStackTrace();
            }

        }
    }
}