package com.example.dashboard;

import java.util.HashMap;

public class ItemContainer implements ItemElement {
    private String name, price, locationX, locationY, length, width, height;
    //map of item containers being contained in a container.
    private HashMap<String, Item> itemsMap = new HashMap<>();

    public ItemContainer(String name, String price, String locationX, String locationY, String length, String width, String height) {
        this.name = name;
        this.price = price;
        this.locationX = locationX;
        this.locationY = locationY;
        this.length = length;
        this.width = width;
        this.height = height;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getLocationX() {
        return locationX;
    }

    public void setLocationX(String locationX) {
        this.locationX = locationX;
    }

    public String getLocationY() {
        return locationY;
    }

    public void setLocationY(String locationY) {
        this.locationY = locationY;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public String getWidth() {
        return width;
    }

    public void setWidth(String width) {
        this.width = width;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public void addItemToMap(String newItemName, Item newItem){
        itemsMap.put(newItemName, newItem);
    }

    public Item getItemFromMap(String queryName){
        return itemsMap.get(queryName);
    }

    public void removeItemFromMap(String queryName){
        itemsMap.remove(queryName);
    }

    public HashMap<String, Item> getItemsMap() {
        return itemsMap;
    }

    @Override
    public int accept(ItemVisitor visitor) {
        return visitor.visit(this);
    }
}
