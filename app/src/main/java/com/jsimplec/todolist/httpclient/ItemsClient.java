package com.jsimplec.todolist.httpclient;

import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.model.Item;
import com.jsimplec.todolist.model.ItemList;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class ItemsClient {

    public static final ItemsClient ITEMS_CLIENT = new ItemsClient();

    private ItemsClient() {
    }

    public void loadData(String token, SuccessErrorCallBack<List<ItemList>> callBack) {
        Item item = new Item("3", "Die", "Now", "In progress", "just now");
        callBack.onSuccess(Arrays.asList(new ItemList("123", "Item", "aykhan", "today", Arrays.asList(item))));
    }
}
