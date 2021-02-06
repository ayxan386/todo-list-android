package com.jsimplec.todolist.model;

import java.util.ArrayList;
import java.util.List;

public class ItemList {
    private final String id;
    private final String name;
    private final String username;
    private final String updateDate;
    private final List<Item> items;

    public ItemList(String id, String name, String username, String updateDate, List<Item> items) {
        this.id = id;
        this.name = name;
        this.username = username;
        this.updateDate = updateDate;
        this.items = items;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername() {
        return username;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    public List<Item> getItems() {
        return items;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ItemList itemList = (ItemList) o;
        return id.equals(itemList.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    @Override
    public String toString() {
        return String.format("ItemList{id='%s'}", id);
    }

    public ItemList addItem(Item item) {
        ArrayList<Item> newItems = new ArrayList<>(this.items);
        newItems.add(item);
        return new ItemList(id, name, username, updateDate, newItems);
    }
}
