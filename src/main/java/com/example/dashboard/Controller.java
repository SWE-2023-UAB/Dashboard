package com.example.dashboard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.image.Image;

public class Controller implements Initializable {
    //Hierarchy ==> TreeView
    public TreeView treeView;
    //Set base Root
    TreeItem<String> rootItem = new TreeItem<>("Farm");
    //Create hashmap with name as key and object as value
    HashMap<String, ItemContainer> containerMap = new HashMap<String, ItemContainer>();

    @FXML
    //Show selected item from hierarchy
    public void SelectItem(){
        TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
        if (item != null) {
            System.out.println(item);
        }
    }
    public Pane visualPane;
    public ImageView droneImage;

    @FXML
    // Create dropdown for item and itemcontainer
    public ChoiceBox<String> itemContainerBox;
    public ChoiceBox<String> itemBox;
    public String[] itemContainerOptions = {
            "Add Item-Container",
            "Delete Item-Container",
//            "Add Item",
//            "Change Name",
//            "Change Price",
//            "Change Location",
//            "Change Dimensions"
    };
    public String[] itemOptions = {
            "Add Item",
            "Delete Item",
            "Change Name",
            "Change Price",
            "Change Location",
            "Change Dimensions"
    };

    @Override
    //Allow items to be selected from dropdown and define click functions
    public void initialize(URL url, ResourceBundle resourceBundle) {
        itemBox.getItems().addAll(itemOptions);
        itemContainerBox.getItems().addAll(itemContainerOptions);
        itemBox.setOnAction(this::GetItemSelection);
        itemContainerBox.setOnAction(this::GetContainerSelection);
        treeView.setRoot(rootItem);
        //making farm an item in the map
        containerMap.put(rootItem.getValue(), new ItemContainer(rootItem.getValue(), "", "0", "0", "800", "400" , ""));


        //Add default item containers and items
        String commandCenterName = "Command Center";
        ItemContainer commandCenter = new ItemContainer(commandCenterName, "0", "317", "10", "166", "129", "0");
        //Add an item container called "Command Center" as a child of root
        TreeItem<String> branch = new TreeItem<>(commandCenterName);
        rootItem.getChildren().addAll(branch);
        containerMap.put(commandCenterName, commandCenter);

        Group commandCenterGroup = new Group();
        //Create the rectangle for the command center in the border pane
        Rectangle commandCenterRectangle = new Rectangle();
        commandCenterRectangle.setX(Double.parseDouble(containerMap.get(commandCenterName).getLocationX()));
        commandCenterRectangle.setY(Double.parseDouble(containerMap.get(commandCenterName).getLocationY()));
        commandCenterRectangle.setWidth(Double.parseDouble(containerMap.get(commandCenterName).getLength()));
        commandCenterRectangle.setHeight(Double.parseDouble(containerMap.get(commandCenterName).getWidth()));
        commandCenterRectangle.setFill(null);
        commandCenterRectangle.setStroke(Color.RED);
        Text commandCenterText = new Text(containerMap.get(commandCenterName).getName());
        commandCenterText.setFill(Color.BLACK);
        //Set the position of the text
        commandCenterText.setX(Double.parseDouble(containerMap.get(commandCenterName).getLocationX()) + 5);
        commandCenterText.setY(Double.parseDouble(containerMap.get(commandCenterName).getLocationY()) + 15);
        //Add the rectangle and text to the group
        commandCenterGroup.getChildren().addAll(commandCenterRectangle, commandCenterText);
        visualPane.getChildren().add(commandCenterGroup);

        //Add an item container called "Drone box" as a child of "Command Center"
        String droneBoxName = "Drone Box";
        ItemContainer droneBox = new ItemContainer(droneBoxName, "0", "360", "55", "80", "80", "0");
        TreeItem<String> branch2 = new TreeItem<>(droneBoxName);
        branch.getChildren().addAll(branch2);
        containerMap.put(droneBoxName, droneBox);

        //Rectangle for drone box
        Group droneBoxGroup = new Group();
        Rectangle droneBoxRectangle = new Rectangle();
        droneBoxRectangle.setX(Double.parseDouble(containerMap.get(droneBoxName).getLocationX()));
        droneBoxRectangle.setY(Double.parseDouble(containerMap.get(droneBoxName).getLocationY()));
        droneBoxRectangle.setWidth(Double.parseDouble(containerMap.get(droneBoxName).getLength()));
        droneBoxRectangle.setHeight(Double.parseDouble(containerMap.get(droneBoxName).getWidth()));
        droneBoxRectangle.setFill(null);
        droneBoxRectangle.setStroke(Color.RED);
        Text droneBoxText = new Text(containerMap.get(droneBoxName).getName());
        droneBoxText.setFill(Color.BLACK);
        //Set the position of the text
        droneBoxText.setX(Double.parseDouble(containerMap.get(droneBoxName).getLocationX()) + 5);
        droneBoxText.setY(Double.parseDouble(containerMap.get(droneBoxName).getLocationY()) + 15);
        //Add the rectangle and text to the group
        droneBoxGroup.getChildren().addAll(droneBoxRectangle, droneBoxText);
        visualPane.getChildren().add(droneBoxGroup);

        //Add an item called "Drone" as a child of "Drone Box"
        String droneName = "Drone";
        /*THE DRONE IS CREATED AS AN ITEMCONTAINER?
        IS THIS CORRECT?
        SHOULD IT NOT BE AN ITEM?
        HOW DO WE DIFFERENTIATE?*/
        Item drone = new Item(droneName, "0", "0", "0", "0", "0", "0");
        TreeItem<String> leaf = new TreeItem<>(droneName);
        branch2.getChildren().addAll(leaf);
        //adding drone to farms item map.
        containerMap.get(rootItem.getValue()).addItemToMap(droneName, drone);
        String imageURL = "https://pngimg.com/uploads/drone/drone_PNG116.png";
        Image image = new Image(imageURL);
        droneImage.setImage(image);
        //Set the position of the drone image using the coordinates of the drone box
        droneImage.setLayoutX(Double.parseDouble(containerMap.get(droneBoxName).getLocationX()) + 15);
        droneImage.setLayoutY(Double.parseDouble(containerMap.get(droneBoxName).getLocationY()) + 15);
    }

    //If item from item dropdown is selected
    public void GetContainerSelection (ActionEvent event){
        //Set selected item to variable
        String containerSelection = itemContainerBox.getValue();
        switch(containerSelection){
            case "Add Item-Container" -> {
                // Check if there's a selected item in the tree view
                TreeItem<String> selectedItem = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
                //Check if selected item is null, not command center, and that is an ItemContainer object.
                if (selectedItem != null && !selectedItem.getValue().equals("Command Center") && containerMap.get(selectedItem.getValue()) != null) {
                    //Error handling for item containers not being added to containers.
                    //TRIED IMPLEMENTING TYPE CHECK HERE TO PREVENT ITEM CONTAINERS GETTING PLACED IN ITEMS
//                    if(containerMap.get(selectedItem.getValue()).getClass() != ItemContainer.class){
//                        System.out.println("CONTAINERS CAN ONLY BE ADDED TO CONTAINERS!");
//                        return;
//                    }
                    //Make Dialog Pop Up for Item Container
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("item-container.fxml"));
                        DialogPane icDialogue = fxmlLoader.load();
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setDialogPane(icDialogue);
                        dialog.setTitle("Item Container");
                        Optional<ButtonType> clickedButton = dialog.showAndWait();
                        //Submit dialogue pop up
                        if (clickedButton.get() == ButtonType.FINISH) {
                            System.out.println("Finished Pressed");
                            //Run IC-Controller
                            ICController icController = fxmlLoader.getController();
                            //Add item to hierarchy
                            TreeItem<String> branch = new TreeItem<>(icController.itemC.getName());
                            selectedItem.getChildren().add(branch);
                            //Add item to Hashmap with name as key and object as value
                            containerMap.put(icController.itemC.getName(), icController.itemC);
                        }
                    } catch (IOException e) {
                        // Handle the IOException
                        e.printStackTrace();
                    }
                }
            }
            case "Delete Item-Container" -> {
                //Get current selected node
                TreeItem curr = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                //Check if selected item is null, not command center, and that is an ItemContainer object.
                if(curr != null && !curr.getValue().equals("Command Center") && !curr.getValue().equals("Farm") && containerMap.get(curr.getValue()) != null){
                    //Remove children (includes self), also remove it from hashmap
                    curr.getParent().getChildren().remove(curr);
                    containerMap.remove(curr.getValue());
                }
            }
        }
    }

    //if item from item-container dropdown is selected
    public void GetItemSelection (ActionEvent event){
        //Set selected item to variable
        String itemSelection = itemBox.getValue();
        switch (itemSelection) {
            case "Add Item" -> {
                //Make dialogue pop up for item.
                TreeItem<String> selectedItem = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
                //Check if selected item is null, not command center, and that is an ItemContainer object.
                if (selectedItem != null && !selectedItem.getValue().equals("Command Center") && containerMap.get(selectedItem.getValue()) != null) {
                    try {
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("item.fxml"));
                        DialogPane icDialogue = fxmlLoader.load();
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setDialogPane(icDialogue);
                        dialog.setTitle("Item");
                        Optional<ButtonType> clickedButton = dialog.showAndWait();
                        //Submit dialogue pop up
                        if (clickedButton.get() == ButtonType.FINISH) {
                            System.out.println("Finished Pressed");
                            IController iController = fxmlLoader.getController();
                            //Create leaf node
                            TreeItem<String> leaf = new TreeItem<>(iController.item.getName());
                            //Check which item is currently selected in hierarchy
                            //add leaf node to item currently selected.
                            selectedItem.getChildren().addAll(leaf);
                            System.out.println(selectedItem.getValue());
                            //Add item to Hashmap with name as key and object as value
                            System.out.println(containerMap.get(selectedItem.getValue()));
                            containerMap.get(selectedItem.getValue()).addItemToMap(iController.item.getName(), iController.item);
                            System.out.println(containerMap.get(selectedItem.getValue()).getItemFromMap(iController.item.getName()));
                        }
                    } catch (IOException e) {
                        // Handle the IOException, e.g., by printing an error message
                        e.printStackTrace();
                    }
                }
            }

            //not sure if this is working

//            case "Delete Item" -> {
//                //Get current selected node
//                TreeItem curr = (TreeItem) treeView.getSelectionModel().getSelectedItem();
//                //Check if selected item is null, not command center, and that is an ItemContainer object.
//                if(curr != null && !curr.getValue().equals("Command Center") && !curr.getValue().equals("Farm") && containerMap.get(curr.getValue()) != null){
//                    //Remove children (includes self), also remove it from hashmap
//                    curr.getParent().getChildren().remove(curr);
//                    containerMap.remove(curr.getValue());
//                }
//            }

            case "Change name" -> {
                //Change the name of the current container item WIP
                try {
                    TreeItem curr = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    //Check if selected item is null, not command center, and not Farm
                    if (curr != null && !curr.getValue().equals("Command Center") && !curr.getValue().equals("Farm")) {
                        //loading name change popup
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("change-name.fxml"));
                        DialogPane icDialogue = fxmlLoader.load();
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setDialogPane(icDialogue);
                        dialog.setTitle("Change Name");
                        Optional<ButtonType> clickedButton = dialog.showAndWait();

                        //checking to see if button is clicked
                        if (clickedButton.get() == ButtonType.FINISH) {
                            ChangeNameController changeNameController = fxmlLoader.getController();
                            String newName = changeNameController.getNewName();
                            //creating a copy of the original item for updating
                            ItemContainer updatedContainer = containerMap.get(curr.getValue());
                            //Removing old instance
                            containerMap.remove(curr.getValue());
                            //updating name and setting it in tree node
                            updatedContainer.setName(newName);
                            //inserting new version into hashmap
                            containerMap.put(updatedContainer.getName(), updatedContainer);
                            curr.setValue(newName);
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "Change Price" -> {
                try {
                    TreeItem curr = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    //Check if selected item is null, not command center, and not Farm
                    if (curr != null && !curr.getValue().equals("Command Center") && !curr.getValue().equals("Farm")) {
                        //loading price change popup
                        System.out.println("Old price: " + containerMap.get(curr.getValue()).getPrice());
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("change-price.fxml"));
                        DialogPane icDialogue = fxmlLoader.load();
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setDialogPane(icDialogue);
                        dialog.setTitle("Change Price");
                        Optional<ButtonType> clickedButton = dialog.showAndWait();

                        if (clickedButton.get() == ButtonType.FINISH) {
                            ChangePriceController changePriceController = fxmlLoader.getController();
                            //getting new price
                            String newPrice = changePriceController.getNewPrice();
                            //updating price with new price
                            containerMap.get(curr.getValue()).setPrice(newPrice);
                            System.out.println("New Price: " + containerMap.get(curr.getValue()).getPrice());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "Change Location" -> {
                try {
                    TreeItem curr = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    //Check if selected item is null, not command center, and not Farm
                    if (curr != null && !curr.getValue().equals("Command Center") && !curr.getValue().equals("Farm")) {
                        //loading up the coordinates popup
                        System.out.println("Old Coords: (" + containerMap.get(curr.getValue()).getLocationX()
                                + ", (" + containerMap.get(curr.getValue()).getLocationY() + ")");
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("change-location.fxml"));
                        DialogPane icDialogue = fxmlLoader.load();
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setDialogPane(icDialogue);
                        dialog.setTitle("Change Location");
                        Optional<ButtonType> clickedButton = dialog.showAndWait();
                        if (clickedButton.get() == ButtonType.FINISH) {
                            ChangeLocationController changeLocationController = fxmlLoader.getController();
                            //getting new x and y coordinates.
                            String newX = changeLocationController.getNewXCoord();
                            String newY = changeLocationController.getNewYCoord();
                            //updating x and y
                            containerMap.get(curr.getValue()).setLocationX(newX);
                            containerMap.get(curr.getValue()).setLocationY(newY);
                            System.out.println("New Coords: (" + containerMap.get(curr.getValue()).getLocationX()
                                    + ", (" + containerMap.get(curr.getValue()).getLocationY() + ")");
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "Change Dimensions" -> {
                try {
                    TreeItem curr = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    //Check if selected item is null, not command center, and not Farm
                    if (curr != null && !curr.getValue().equals("Command Center") && !curr.getValue().equals("Farm")) {
                        //loading up the dimensions popup
                        System.out.println("Old Dimensions: " + containerMap.get(curr.getValue()).getLength()
                                + " x " + containerMap.get(curr.getValue()).getWidth() + " x "
                                + containerMap.get(curr.getValue()).getHeight());
                        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("change-dimensions.fxml"));
                        DialogPane icDialogue = fxmlLoader.load();
                        Dialog<ButtonType> dialog = new Dialog<>();
                        dialog.setDialogPane(icDialogue);
                        dialog.setTitle("Change Dimensions");
                        Optional<ButtonType> clickedButton = dialog.showAndWait();
                        if (clickedButton.get() == ButtonType.FINISH) {
                            ChangeDimensionsController changeDimensionsController = fxmlLoader.getController();
                            //getting new x and y coordinates.
                            String newLength = changeDimensionsController.getNewLength();
                            String newWidth = changeDimensionsController.getNewWidth();
                            String newHeight = changeDimensionsController.getNewHeight();

                            //updating dimensions
                            containerMap.get(curr.getValue()).setLength(newLength);
                            containerMap.get(curr.getValue()).setWidth(newWidth);
                            containerMap.get(curr.getValue()).setHeight(newHeight);

                            System.out.println("New Dimensions: " + containerMap.get(curr.getValue()).getLength()
                                    + " x " + containerMap.get(curr.getValue()).getWidth() + " x "
                                    + containerMap.get(curr.getValue()).getHeight());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}