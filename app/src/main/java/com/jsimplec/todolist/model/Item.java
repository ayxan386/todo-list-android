package com.jsimplec.todolist.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Item implements Parcelable {
    private final String id;
    private final String title;
    private final String content;
    private final String status;
    private final String updateDate;

    public Item(String id, String title, String content, String status, String updateDate) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.status = status;
        this.updateDate = updateDate;
    }

    protected Item(Parcel in) {
        id = in.readString();
        title = in.readString();
        content = in.readString();
        status = in.readString();
        updateDate = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(title);
        dest.writeString(content);
        dest.writeString(status);
        dest.writeString(updateDate);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Item> CREATOR = new Creator<Item>() {
        @Override
        public Item createFromParcel(Parcel in) {
            return new Item(in);
        }

        @Override
        public Item[] newArray(int size) {
            return new Item[size];
        }
    };

    public Item buildItem(String content, String status) {
        return new Item(id, title, content, status, updateDate);
    }

    public static boolean getActiveStatus(String status) {
        return status.equals("DONE");
    }

    public static String getStatusFromBool(boolean isChecked) {
        return isChecked ? "DONE" : "IN_PROGRESS";
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
