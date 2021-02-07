package com.jsimplec.todolist.util;

import com.google.gson.reflect.TypeToken;
import com.jsimplec.todolist.model.Item;
import com.jsimplec.todolist.model.ItemList;
import com.jsimplec.todolist.model.RestResponseDto;

import java.lang.reflect.Type;
import java.util.List;

public class HttpUtils {
    public static String makeAuthHeader(String token) {
        return String.format("Bearer %s", token);
    }

    public static final Type REST_RESPONSE_LIST_ITEMLIST_TYPE = new TypeToken<RestResponseDto<List<ItemList>>>() {
    }.getType();

    public static final Type REST_RESPONSE_ITEM_TYPE = new TypeToken<RestResponseDto<Item>>() {
    }.getType();
    public static final Type REST_RESPONSE_ITEMLIST_TYPE = new TypeToken<RestResponseDto<ItemList>>() {
    }.getType();
}
