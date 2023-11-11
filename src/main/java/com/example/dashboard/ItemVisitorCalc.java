package com.example.dashboard;

public class ItemVisitorCalc implements ItemVisitor {
    //retrieving price of item.
    @Override
    public int visit(Item item) {
        return Integer.parseInt(item.getPrice());
    }

    //calculating price of containers
    @Override
    public int visit(ItemContainer itemContainer) {
        int totalPrice = Integer.parseInt(itemContainer.getPrice());
        for(Item item : itemContainer.getItemsMap().values()){
            totalPrice += item.accept(this);
        }
        return totalPrice;
    }
}
