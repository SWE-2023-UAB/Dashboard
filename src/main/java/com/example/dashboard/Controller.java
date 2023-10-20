package com.example.dashboard;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;

import javafx.scene.Group;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
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
    HashMap<String, ItemContainer> itemMap = new HashMap<String, ItemContainer>();
    
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
            "Add Item",
            "Delete Item-Container",
            "Change Name",
            "Change Price",
            "Change Location",
            "Change Dimensions"
    };
    public String[] itemOptions = {
            "Delete Item",
            "Change name",
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

        //Add default item containers and items
        String commandCenterName = "Command Center";
        ItemContainer commandCenter = new ItemContainer(commandCenterName, "0", "317", "10", "166", "129", "0");
        //Add an item container called "Command Center" as a child of root
        TreeItem<String> branch = new TreeItem<>(commandCenterName);
        rootItem.getChildren().addAll(branch);
        itemMap.put(commandCenterName, commandCenter);

        Group commandCenterGroup = new Group();
        //Create the rectangle for the command center in the border pane
        Rectangle commandCenterRectangle = new Rectangle();
        commandCenterRectangle.setX(Double.parseDouble(itemMap.get(commandCenterName).getLocationX()));
        commandCenterRectangle.setY(Double.parseDouble(itemMap.get(commandCenterName).getLocationY()));
        commandCenterRectangle.setWidth(Double.parseDouble(itemMap.get(commandCenterName).getLength()));
        commandCenterRectangle.setHeight(Double.parseDouble(itemMap.get(commandCenterName).getWidth()));
        commandCenterRectangle.setFill(null);
        commandCenterRectangle.setStroke(Color.RED);
        Text commandCenterText = new Text(itemMap.get(commandCenterName).getName());
        commandCenterText.setFill(Color.BLACK);
        //Set the position of the text
        commandCenterText.setX(Double.parseDouble(itemMap.get(commandCenterName).getLocationX()) + 5);
        commandCenterText.setY(Double.parseDouble(itemMap.get(commandCenterName).getLocationY()) + 15);
        //Add the rectangle and text to the group
        commandCenterGroup.getChildren().addAll(commandCenterRectangle, commandCenterText);
        visualPane.getChildren().add(commandCenterGroup);

        //Add an item container called "Drone box" as a child of "Command Center"
        String droneBoxName = "Drone Box";
        ItemContainer droneBox = new ItemContainer(droneBoxName, "0", "360", "55", "80", "80", "0");
        TreeItem<String> branch2 = new TreeItem<>(droneBoxName);
        branch.getChildren().addAll(branch2);
        itemMap.put(droneBoxName, droneBox);

        //Rectangle for drone box
        Group droneBoxGroup = new Group();
        Rectangle droneBoxRectangle = new Rectangle();
        droneBoxRectangle.setX(Double.parseDouble(itemMap.get(droneBoxName).getLocationX()));
        droneBoxRectangle.setY(Double.parseDouble(itemMap.get(droneBoxName).getLocationY()));
        droneBoxRectangle.setWidth(Double.parseDouble(itemMap.get(droneBoxName).getLength()));
        droneBoxRectangle.setHeight(Double.parseDouble(itemMap.get(droneBoxName).getWidth()));
        droneBoxRectangle.setFill(null);
        droneBoxRectangle.setStroke(Color.RED);
        Text droneBoxText = new Text(itemMap.get(droneBoxName).getName());
        droneBoxText.setFill(Color.BLACK);
        //Set the position of the text
        droneBoxText.setX(Double.parseDouble(itemMap.get(droneBoxName).getLocationX()) + 5);
        droneBoxText.setY(Double.parseDouble(itemMap.get(droneBoxName).getLocationY()) + 15);
        //Add the rectangle and text to the group
        droneBoxGroup.getChildren().addAll(droneBoxRectangle, droneBoxText);
        visualPane.getChildren().add(droneBoxGroup);

        //Add an item called "Drone" as a child of "Drone Box"
        String droneName = "Drone";
        /*THE DRONE IS CREATED AS AN ITEMCONTAINER?
        IS THIS CORRECT?
        SHOULD IT NOT BE AN ITEM?
        HOW DO WE DIFFERENTIATE?*/
        ItemContainer drone = new ItemContainer(droneName, "0", "0", "0", "0", "0", "0");
        TreeItem<String> leaf = new TreeItem<>(droneName);
        branch2.getChildren().addAll(leaf);
        itemMap.put(droneName, drone);
        String imageURL = "https://pngimg.com/uploads/drone/drone_PNG116.png";
        Image image = new Image(imageURL);
        droneImage.setImage(image);
        //Set the position of the drone image using the coordinates of the drone box
        droneImage.setLayoutX(Double.parseDouble(itemMap.get(droneBoxName).getLocationX()) + 15);
        droneImage.setLayoutY(Double.parseDouble(itemMap.get(droneBoxName).getLocationY()) + 15);
    }

    //If item from item dropdown is selected
    public void GetItemSelection (ActionEvent event){
        String itemSelection = itemContainerBox.getValue();
    }

    //if item from item-container dropdown is selected
    public void GetContainerSelection (ActionEvent event){
        //Set selected item to variable
        String containerSelection = itemContainerBox.getValue();
        switch (containerSelection) {
            case "Add Item-Container" -> {
                // Check if there's a selected item in the tree view
                TreeItem<String> selectedItem = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
                //Check if selected item is null
                if (selectedItem != null) {
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
                            itemMap.put(icController.itemC.getName(), icController.itemC);
                        }
                    } catch (IOException e) {
                        // Handle the IOException
                        e.printStackTrace();
                    }
                }
            }
            case "Add Item" -> {
                //Make dialogue pop up for item.
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
                        TreeItem<String> leaf = new TreeItem<>(iController.itemC.getName());
                        //Check which item is currently selected in hierarchy
                        TreeItem<String> item = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
                        //add leaf node to item currently selected.
                        item.getChildren().addAll(leaf);
                        //Add item to Hashmap with name as key and object as value
                        itemMap.put(iController.itemC.getName(), iController.itemC);
                    }
                } catch (IOException e) {
                    // Handle the IOException, e.g., by printing an error message
                    e.printStackTrace();
                }
            }
            case "Delete Item-Container" -> {
                //Get current selected node
                TreeItem curr = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                //Remove children (includes self)
                boolean remove = curr.getParent().getChildren().remove(curr);
            }
            case "Change name" -> {
                //Change the name of the current container item WIP
                try {
                    TreeItem curr = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    if (curr != null) {
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
                            ItemContainer updatedContainer = itemMap.get(curr.getValue());
                            //Removing old instance
                            itemMap.remove(curr.getValue());
                            //updating name and setting it in tree node
                            updatedContainer.setName(newName);
                            //inserting new version into hashmap
                            itemMap.put(updatedContainer.getName(), updatedContainer);
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
                    if (curr != null) {
                        //loading price change popup
                        System.out.println("Old price: " + itemMap.get(curr.getValue()).getPrice());
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
                            itemMap.get(curr.getValue()).setPrice(newPrice);
                            System.out.println("New Price: " + itemMap.get(curr.getValue()).getPrice());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "Change Location" -> {
                try {
                    TreeItem curr = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    if (curr != null) {
                        //loading up the coordinates popup
                        System.out.println("Old Coords: (" + itemMap.get(curr.getValue()).getLocationX()
                                + ", (" + itemMap.get(curr.getValue()).getLocationY() + ")");
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
                            itemMap.get(curr.getValue()).setLocationX(newX);
                            itemMap.get(curr.getValue()).setLocationY(newY);
                            System.out.println("New Coords: (" + itemMap.get(curr.getValue()).getLocationX()
                                    + ", (" + itemMap.get(curr.getValue()).getLocationY() + ")");
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
            case "Change Dimensions" -> {
                try {
                    TreeItem curr = (TreeItem) treeView.getSelectionModel().getSelectedItem();
                    if (curr != null) {
                        //loading up the dimensions popup
                        System.out.println("Old Dimensions: " + itemMap.get(curr.getValue()).getLength()
                                + " x " + itemMap.get(curr.getValue()).getWidth() + " x "
                                + itemMap.get(curr.getValue()).getHeight());
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
                            itemMap.get(curr.getValue()).setLength(newLength);
                            itemMap.get(curr.getValue()).setWidth(newWidth);
                            itemMap.get(curr.getValue()).setHeight(newHeight);

                            System.out.println("New Dimensions: " + itemMap.get(curr.getValue()).getLength()
                                    + " x " + itemMap.get(curr.getValue()).getWidth() + " x "
                                    + itemMap.get(curr.getValue()).getHeight());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

}