package com.example.dashboard;
import javafx.animation.*;
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
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.util.Duration;

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
    public Button fly;
    public Button home;
    public Button scan;

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

        //Draw a rectangle for the command center
        DrawRectangle(commandCenter);

        //Add an item container called "Drone box" as a child of "Command Center"
        String droneBoxName = "Drone Box";
        ItemContainer droneBox = new ItemContainer(droneBoxName, "0", "360", "55", "80", "80", "0");
        TreeItem<String> branch2 = new TreeItem<>(droneBoxName);
        branch.getChildren().addAll(branch2);
        containerMap.put(droneBoxName, droneBox);

        //Rectangle for drone box
        DrawRectangle(droneBox);

        //Add an item called "Drone" as a child of "Drone Box"
        String droneName = "Drone";
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
                    if (!(containerMap.get(selectedItem.getValue()) instanceof ItemContainer)) {
                        throw new RuntimeException("Item containers can only be added to containers.");
                    }
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
                            //Draw rectangle for item container
                            DrawRectangle(icController.itemC);
                        }
                    } catch (IOException e) {
                        // Handle the IOException
                        e.printStackTrace();
                    }
                }
            }
            case "Delete Item-Container" -> {
                try {
                    TreeItem<String> selectedItem = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();

                    if (selectedItem != null && !selectedItem.getValue().equals("Command Center")) {
                        TreeItem<String> parent = selectedItem.getParent();
                        String containerName = selectedItem.getValue();
                        if (parent != null) {
                            //Check if the selected item is a container and in the container map
                            if (containerMap.containsKey(containerName) && containerMap.get(containerName) instanceof ItemContainer) {
                                ItemContainer container = (ItemContainer) containerMap.get(containerName);
                                Map<String, Item> itemsMap = container.getItemsMap();

                                //Remove all items in the container from the visual pane
                                for (String itemName : itemsMap.keySet()) {
                                    deleteRectangle(itemName);
                                }
                                //Delete the rectangle for the container
                                deleteRectangle(containerName);
                                //Remove the item from the parent
                                parent.getChildren().remove(selectedItem);
                                //Remove the item from the container map
                                containerMap.remove(containerName);
                            }
                        }
                    }
                }
                catch (Exception e) {
                    e.printStackTrace();
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
                            //Draw rectangle for item
                            DrawRectangle(iController.item);
                        }
                    } catch (IOException e) {
                        // Handle the IOException, e.g., by printing an error message
                        e.printStackTrace();
                    }
                }
            }

            case "Delete Item" -> {
                try {
                    TreeItem<String> selectedItem = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();

                    if (selectedItem != null) {
                        TreeItem<String> parent = selectedItem.getParent();

                        if (parent != null) {
                            String itemName = selectedItem.getValue();
                            ItemContainer parentContainer = containerMap.get(parent.getValue());

                            if (parentContainer != null) {
                                // Check if the selected item exists in the parent container
                                if (parentContainer.getItemFromMap(itemName) != null) {
                                    // Remove the item from the parent container's item map
                                    parentContainer.removeItemFromMap(itemName);
                                    // Remove the item from the hierarchy
                                    parent.getChildren().remove(selectedItem);
                                    // Remove the rectangle from the visual pane
                                    deleteRectangle(itemName);
                                }
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            case "Change Name" -> {
                //Change the name of the current container item WIP
                try {
                    TreeItem<String> curr = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
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
                            if (updatedContainer != null){
                                Group updatedGroup = groupMap.get(curr.getValue());
                                //Removing old instance
                                containerMap.remove(curr.getValue());
                                groupMap.remove(curr.getValue());
                                //updating name and setting it in tree node
                                updatedContainer.setName(newName);
                                //inserting new version into hashmap
                                containerMap.put(updatedContainer.getName(), updatedContainer);
                                groupMap.put(updatedContainer.getName(), updatedGroup);
                                curr.setValue(newName);
                                //print out to console to check if it worked
                                System.out.println("New Name: " + containerMap.get(curr.getValue()).getName());
                                //updating rectangle
                                updateRectangle(updatedContainer.getName());
                            } else {
                                String itemName = curr.getParent().getValue();
                                ItemContainer container = containerMap.get(itemName);
                                if (container != null){
                                    Item item = container.getItemFromMap(itemName);
                                    if (item != null){
                                        Group updatedGroup = groupMap.get(curr.getValue());
                                        //Removing old instance
                                        groupMap.remove(curr.getValue());
                                        //updating name and setting it in tree node
                                        updatedContainer.setName(item.getName());
                                        //inserting new version into hashmap
                                        groupMap.put(item.getName(), updatedGroup);
                                        curr.setValue(newName);
                                        //print out to console to check if it worked
                                        System.out.println("New Name: " + containerMap.get(curr.getValue()).getName());
                                        //updating rectangle
                                        updateRectangle(updatedContainer.getName());
                                    }
                                }
                            }
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
                            //updating rectangle
                            updateRectangle(curr.getValue().toString());
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
                            //updating rectangle
                            updateRectangle(curr.getValue().toString());
                        }
                    }
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }

    //Map to store groups to keep track of rectangles
    public Map<String, Group> groupMap = new HashMap<String, Group>();

    //Method to draw the rectangles that takes itemContainer as a parameter
    public void DrawRectangle(ItemContainer itemContainer){
        //Create a group for the rectangle and text
        Group group = new Group();
        //Create the rectangle for the item container in the border pane
        Rectangle rectangle = new Rectangle();
        rectangle.setX(Double.parseDouble(itemContainer.getLocationX()));
        rectangle.setY(Double.parseDouble(itemContainer.getLocationY()));
        rectangle.setWidth(Double.parseDouble(itemContainer.getLength()));
        rectangle.setHeight(Double.parseDouble(itemContainer.getWidth()));
        rectangle.setFill(null);
        rectangle.setStroke(Color.RED);
        Text text = new Text(itemContainer.getName());
        text.setFill(Color.BLACK);
        //Set the position of the text
        text.setX(Double.parseDouble(itemContainer.getLocationX()) + 5);
        text.setY(Double.parseDouble(itemContainer.getLocationY()) + 15);
        //Add the rectangle and text to the group
        group.getChildren().addAll(rectangle, text);
        visualPane.getChildren().add(group);
        //Add the group to the group map
        groupMap.put(itemContainer.getName(), group);
    }
    //Overloading DrawRectangle method to draw the rectangles that takes item as a parameter
    public void DrawRectangle(Item item){
        //Create a group for the rectangle and text
        Group group = new Group();
        //Create the rectangle for the item container in the border pane
        Rectangle rectangle = new Rectangle();
        rectangle.setX(Double.parseDouble(item.getLocationX()));
        rectangle.setY(Double.parseDouble(item.getLocationY()));
        rectangle.setWidth(Double.parseDouble(item.getLength()));
        rectangle.setHeight(Double.parseDouble(item.getWidth()));
        rectangle.setFill(null);
        rectangle.setStroke(Color.RED);
        Text text = new Text(item.getName());
        text.setFill(Color.BLACK);
        //Set the position of the text
        text.setX(Double.parseDouble(item.getLocationX()) + 5);
        text.setY(Double.parseDouble(item.getLocationY()) + 15);
        //Add the rectangle and text to the group
        group.getChildren().addAll(rectangle, text);
        visualPane.getChildren().add(group);
        //Add the group to the group map
        groupMap.put(item.getName(), group);
    }
    //Method to delete the rectangles that takes itemContainer as a parameter
    public void deleteRectangle(String name) {
        Group group = groupMap.get(name);
        if (group != null) {
            visualPane.getChildren().remove(group);
            groupMap.remove(name);
        }
    }
    //Method to update the rectangles when itemContainer or item is changed
    public void updateRectangle(String name) {
        Group group = groupMap.get(name);
        if (group != null) {
            //Retrieve either the itemContainer or item from the containerMap
            ItemContainer itemContainer = containerMap.get(name);
            System.out.println("HEY YOU GUYS: "+ itemContainer.getName());
            if (itemContainer != null) {
                Rectangle rectangle = (Rectangle) group.getChildren().get(0);
                Text text = (Text) group.getChildren().get(1);
                //Update the rectangle
                rectangle.setX(Double.parseDouble(itemContainer.getLocationX()));
                rectangle.setY(Double.parseDouble(itemContainer.getLocationY()));
                rectangle.setWidth(Double.parseDouble(itemContainer.getLength()));
                rectangle.setHeight(Double.parseDouble(itemContainer.getWidth()));
                //Update the text
                text.setText(itemContainer.getName());
                System.out.println("TEXT NAME: "+ text.getText());
                text.setX(Double.parseDouble(itemContainer.getLocationX()) + 5);
                text.setY(Double.parseDouble(itemContainer.getLocationY()) + 15);
                //Update the group
                group.getChildren().set(0, rectangle);
                group.getChildren().set(1, text);
            }
        }
        else{
            System.out.println("Uh oh spagghetti os");
        }
    }
    //Method to animate the drone to the selected item
    public void droneAnimation(ActionEvent event) {
        TreeItem<String> selectedItem = (TreeItem<String>) treeView.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String itemName = selectedItem.getValue();
            ItemContainer selectedContainer = containerMap.get(itemName);
            if (selectedContainer != null) {
                //Stop any ongoing animation
                droneImage.getTransforms().clear();
                double centerX = Double.parseDouble(selectedContainer.getLocationX()) + Double.parseDouble(selectedContainer.getLength()) / 2 - 25;
                double centerY = Double.parseDouble(selectedContainer.getLocationY()) + Double.parseDouble(selectedContainer.getWidth()) / 2 - 25;
                move(centerX, centerY);
            } else {
                String containerName = selectedItem.getParent().getValue();
                ItemContainer container = containerMap.get(containerName);
                if (container != null) {
                    Item item = container.getItemFromMap(itemName);
                    if (item != null) {
                        //Stop any ongoing animation
                        droneImage.getTransforms().clear();
                        double centerX = Double.parseDouble(item.getLocationX()) + Double.parseDouble(item.getLength()) / 2 - 25;
                        double centerY = Double.parseDouble(item.getLocationY()) + Double.parseDouble(item.getWidth()) / 2 - 25;
                        move(centerX, centerY);
                    }
                }
            }
        }
    }
    //Method to animate the drone to the home position
    public void homeAnimation(ActionEvent event) {
        double homeX = Double.parseDouble(containerMap.get("Drone Box").getLocationX()) + 15;
        double homeY = Double.parseDouble(containerMap.get("Drone Box").getLocationY()) + 15;
        move(homeX, homeY);
    }
    //Method to scan the whole farm
    public void StartScanAnimation(ActionEvent event) {
        double currentX = droneImage.getLayoutX();
        double currentY = droneImage.getLayoutY();

        // Move to (0,0) from starting position
        TranslateTransition moveHome = new TranslateTransition(Duration.seconds(3), droneImage);
        move(0,0);

        double fullX = 720;
        double someY = 100;
        double timeX = 3;
        double timeY = 1;

        // Set an event handler to start the next transition when the first one is finished
        moveHome.setOnFinished(e -> {
            // After the drone is at (0, 0), create another transition to move it to (350, 0)
            TranslateTransition moveFWD = new TranslateTransition(Duration.seconds(timeX), droneImage);
            moveFWD.setByX(fullX);
            moveFWD.play();
            moveFWD.setOnFinished(f -> {
                TranslateTransition moveDWN = new TranslateTransition(Duration.seconds(timeY), droneImage);
                moveDWN.setByY(someY);
                moveDWN.play();
                moveDWN.setOnFinished(g -> {
                    TranslateTransition moveBCK = new TranslateTransition(Duration.seconds(timeX), droneImage);
                    moveBCK.setByX(-fullX);
                    moveBCK.play();
                    moveBCK.setOnFinished(h ->{
                        TranslateTransition moveDWN02 = new TranslateTransition(Duration.seconds(timeY), droneImage);
                        moveDWN02.setByY(someY);
                        moveDWN02.play();
                        moveDWN02.setOnFinished(i -> {
                            TranslateTransition moveFWD02 = new TranslateTransition(Duration.seconds(timeX), droneImage);
                            moveFWD02.setByX(fullX);
                            moveFWD02.play();
                            moveFWD02.setOnFinished(j -> {
                                TranslateTransition moveDWN03 = new TranslateTransition(Duration.seconds(timeY), droneImage);
                                moveDWN03.setByY(someY);
                                moveDWN03.play();
                                moveDWN03.setOnFinished(k -> {
                                    TranslateTransition moveBCK02 = new TranslateTransition(Duration.seconds(timeX), droneImage);
                                    moveBCK02.setByX(-fullX);
                                    moveBCK02.play();
                                    moveBCK02.setOnFinished(l ->{
                                        TranslateTransition moveDWN04 = new TranslateTransition(Duration.seconds(timeY), droneImage);
                                        moveDWN04.setByY(someY);
                                        moveDWN04.play();
                                        moveDWN04.setOnFinished(m -> {
                                            TranslateTransition moveFWD03 = new TranslateTransition(Duration.seconds(timeX), droneImage);
                                            moveFWD03.setByX(fullX);
                                            moveFWD03.play();
                                            moveFWD03.setOnFinished(n -> {
                                                TranslateTransition moveDWN05 = new TranslateTransition(Duration.seconds(timeY), droneImage);
                                                moveDWN05.setByY(someY);
                                                moveDWN05.play();
                                                moveDWN05.setOnFinished(o -> {
                                                    TranslateTransition moveBCK03 = new TranslateTransition(Duration.seconds(timeX), droneImage);
                                                    moveBCK03.setByX(-fullX);
                                                    moveBCK03.play();
                                                });
                                            });
                                        });
                                    });
                                });
                            });
                        });
                    });
                });
            });
        });
        moveHome.play();
    }
    //Method to move the drone to the next position in the scan
    public void move(double x, double y) {
        //Get the current position of the drone
        double currentX = droneImage.getLayoutX();
        double currentY = droneImage.getLayoutY();
        //Calculate the translation relative to the current position
        double translateX = x - currentX;
        double translateY = y - currentY;
        //Create the translation animation
        TranslateTransition translate = new TranslateTransition(Duration.seconds(1), droneImage);
        translate.setDelay(Duration.seconds(1));
        translate.setToX(translateX);
        translate.setToY(translateY);
        //Rotate the drone
        RotateTransition rotate = new RotateTransition(Duration.seconds(1), droneImage);
        rotate.setByAngle(360);
        rotate.setCycleCount(3);
        rotate.setInterpolator(Interpolator.LINEAR);
        //Sequentially play the translation and rotation
        ParallelTransition parallel = new ParallelTransition(rotate, translate);
        parallel.play();
    }
}
