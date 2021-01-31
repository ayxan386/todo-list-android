package com.jsimplec.todolist.model;

import java.util.Objects;

public class Item {
    private String id;
    private String title;
    private String content;
    private String status;
    private String updateDate;

    public Item(String id, String title, String content, String status, String updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.updateDate = updateDate;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public String getStatus() {
        return status;
    }

    public String getUpdateDate() {
        return updateDate;
    }

    @Override
    public String toString() {
        return String.format("Item{title='%s', content='%s', status='%s'}", title, content, status);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return id.equals(item.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }
}
