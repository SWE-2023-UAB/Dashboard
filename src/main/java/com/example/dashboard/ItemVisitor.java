package com.example.dashboard;

public interface ItemVisitor {
    public int visit(Item item);
    public int visit(ItemContainer itemContainer);
}
