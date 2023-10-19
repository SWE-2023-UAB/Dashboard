package com.example.dashboard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.control.*;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;

public class Controller implements Initializable {
    //Hierarchy ==> TreeView
    public TreeView treeView;
    //Set base Root
    TreeItem<String> rootItem = new TreeItem<>("Farm");
    //Create hashmap with name as key and object as value
    HashMap<String, ItemContainer> itemMap = new HashMap<String, ItemContainer>();
    
    @FXML
    //Show selected item from hierarchy
    public void SelectItem(){
        TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
            System.out.println(item);
        }
    }

    @FXML
    // Create dropdown for item and itemcontainer
    public ChoiceBox<String> itemContainerBox;
    public ChoiceBox<String> itemBox;
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
    public String[] itemOptions = {
            "Delete Item",
            "Change name",
            "Change Price",
            "Change Location-X",
            "Change Location-Y",
            "Change Length",
            "Change Width", "Change Height"
    };

    @Override
    //Allow items to be selected from dropdown and define click functions
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemBox.getItems().addAll(itemOptions);
        itemContainerBox.getItems().addAll(itemContainerOptions);
        itemBox.setOnAction(this::GetItemSelection);
        itemContainerBox.setOnAction(this::GetContainerSelection);
        treeView.setRoot(rootItem);
    }

    //If item from item dropdown is selected
    public void GetItemSelection (ActionEvent event){
        String itemSelection = itemContainerBox.getValue();
    }

    //if item from item-container dropdown is selected
    public void GetContainerSelection (ActionEvent event){
        //Set selected item to variable
        String containerSelection = itemContainerBox.getValue();
        if (containerSelection.equals("Add Item-Container")){
            //Make Dialog Pop Up for Item Container
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("item-container.fxml"));
                DialogPane icDialogue = fxmlLoader.load();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(icDialogue);
                dialog.setTitle("Item Container");
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                //Submit dialogue pop up
                if(clickedButton.get() == ButtonType.FINISH){
                    System.out.println("Finished Pressed");
                    //Run IC-Controller
                    ICController icController = fxmlLoader.getController();
                    //Add item to hierarchy
                    TreeItem<String> branch = new TreeItem<>(icController.itemC.name);
                    rootItem.getChildren().addAll(branch);
                    //Add item to Hashmap with name as key and object as value
                    itemMap.put(icController.itemC.name, icController.itemC);
                }
            } catch (IOException e) {
                // Handle the IOException
                e.printStackTrace();
            }

        } else if (containerSelection.equals("Add Item")) {
            //Make dialogue pop up for item.
            try {
                FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("item.fxml"));
                DialogPane icDialogue = fxmlLoader.load();
                Dialog<ButtonType> dialog = new Dialog<>();
                dialog.setDialogPane(icDialogue);
                dialog.setTitle("Item");
                Optional<ButtonType> clickedButton = dialog.showAndWait();
                //Submit dialogue pop up
                if(clickedButton.get() == ButtonType.FINISH){
                    System.out.println("Finished Pressed");
                    IController iController = fxmlLoader.getController();
                    //Create leaf node
                    TreeItem<String> leaf = new TreeItem<>(iController.itemC.name);
                    //Check which item is currently selected in hierarchy
                    TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
                    //add leaf node to item currently selected.
                    item.getChildren().addAll(leaf);
                    //Add item to Hashmap with name as key and object as value
                    itemMap.put(iController.itemC.name, iController.itemC);

                }
            } catch (IOException e) {
                // Handle the IOException, e.g., by printing an error message
                e.printStackTrace();
            }
        } else if (containerSelection.equals("Delete Item-Container")) {
            //Get current selected node
            TreeItem curr = (TreeItem)treeView.getSelectionModel().getSelectedItem();
            //Remove children (includes self)
            boolean remove = curr.getParent().getChildren().remove(curr);
        } else if (containerSelection.equals("Change name")) {
            //Change the name of the current container item WIP
            TreeItem curr = (TreeItem)treeView.getSelectionModel().getSelectedItem();
            if (curr != null) {
                System.out.println(itemMap.get(curr.getValue()).name);
            }
        }
    }

}