package com.jsimplec.todolist.model;

import java.util.List;
import java.util.Objects;

public class ItemList {
    private String id;
    private String name;
    private String username;
    private String updateDate;
    private List<Item> items;

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
}
