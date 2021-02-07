package com.jsimplec.todolist.httpclient;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.jsimplec.todolist.activity.ItemListForm;
import com.jsimplec.todolist.activity.main.MainActivity;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.model.Item;
import com.jsimplec.todolist.model.ItemList;
import com.jsimplec.todolist.model.RestResponseDto;
import com.jsimplec.todolist.util.HttpUtils;
import com.jsimplec.todolist.util.constants.StaticConstants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.jsimplec.todolist.util.HttpUtils.REST_RESPONSE_ITEMLIST_TYPE;
import static com.jsimplec.todolist.util.HttpUtils.REST_RESPONSE_ITEM_TYPE;
import static com.jsimplec.todolist.util.HttpUtils.REST_RESPONSE_LIST_ITEMLIST_TYPE;
import static com.jsimplec.todolist.util.constants.StaticConstants.JSON;
import static com.jsimplec.todolist.util.constants.StaticConstants.MS_BASE_URL;

public class ItemsClient {

    public static final ItemsClient ITEMS_CLIENT = new ItemsClient();
    public static final String REST_SUCCESS_MESSAGE = "success";
    public final OkHttpClient httpClient;
    private final Gson objectMapper = new Gson();

    private ItemsClient() {
        this.httpClient = new OkHttpClient();
    }

    public void loadData(String token, SuccessErrorCallBack<List<ItemList>> callBack) {
        Request request = new Request.Builder()
                .url(String.format("%s/item-list/mine", MS_BASE_URL))
                .header(StaticConstants.AUTHORIZATION_HEADER_NAME, HttpUtils.makeAuthHeader(token))
                .get()
                .build();
        httpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        String responseBody = response.body().string();

                        RestResponseDto<List<ItemList>> responseDto = objectMapper.fromJson(responseBody, REST_RESPONSE_LIST_ITEMLIST_TYPE);
                        if (responseDto.getMessage().equals(REST_SUCCESS_MESSAGE)) {
                            callBack.onSuccess(responseDto.getData());
                        } else {
                            callBack.onError(responseDto.getMessage());
                        }
                    }
                });
    }

    public void updateItem(Item item, SuccessErrorCallBack<String> callBack) {
        SharedPreferences preferences = MainActivity.applicationContext
                .getSharedPreferences(StaticConstants.PREFERENCE_TODO_AUTH, Context.MODE_PRIVATE);
        String token = preferences.getString("token", "123");
        String requestBody = objectMapper.toJson(item);
        Request request = new Request.Builder()
                .url(String.format("%s/item-list/update-item", MS_BASE_URL))
                .header(StaticConstants.AUTHORIZATION_HEADER_NAME, HttpUtils.makeAuthHeader(token))
                .put(RequestBody.create(requestBody, JSON))
                .build();

        httpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        RestResponseDto<Item> responseDto = objectMapper.fromJson(response.body().string(), REST_RESPONSE_ITEM_TYPE);
                        if (responseDto.getMessage().equals(REST_SUCCESS_MESSAGE)) {
                            callBack.onSuccess("success");
                        } else {
                            callBack.onError(responseDto.getMessage());
                        }
                    }
                });
    }

    public void saveItem(Item newItem, SuccessErrorCallBack<Item> callBack) {
        SharedPreferences preferences = MainActivity.applicationContext
                .getSharedPreferences(StaticConstants.PREFERENCE_TODO_AUTH, Context.MODE_PRIVATE);
        String token = preferences.getString("token", "123");
        String requestBody = objectMapper.toJson(newItem);
        Request request = new Request.Builder()
                .url(String.format("%s/item-list/addItem", MS_BASE_URL))
                .header(StaticConstants.AUTHORIZATION_HEADER_NAME, HttpUtils.makeAuthHeader(token))
                .put(RequestBody.create(requestBody, JSON))
                .build();
        httpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        RestResponseDto<Item> responseDto = objectMapper.fromJson(response.body().string(), REST_RESPONSE_ITEM_TYPE);
                        if (responseDto.getMessage().equals(REST_SUCCESS_MESSAGE)) {
                            callBack.onSuccess(responseDto.getData());
                        } else {
                            callBack.onError(responseDto.getMessage());
                        }
                    }
                });
    }

    public void saveList(String title, SuccessErrorCallBack<ItemList> callBack) {
        SharedPreferences preferences = ItemListForm.itemListForm
                .getSharedPreferences(StaticConstants.PREFERENCE_TODO_AUTH, Context.MODE_PRIVATE);
        String token = preferences.getString("token", "123");
        Request request = new Request.Builder()
                .url(String.format("%s/item-list/%s", MS_BASE_URL, title))
                .header(StaticConstants.AUTHORIZATION_HEADER_NAME, HttpUtils.makeAuthHeader(token))
                .post(RequestBody.create("".getBytes(), JSON))
                .build();
        httpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        RestResponseDto<ItemList> responseDto = objectMapper.fromJson(response.body().string(), REST_RESPONSE_ITEMLIST_TYPE);
                        if (responseDto.getMessage().equals(REST_SUCCESS_MESSAGE)) {
                            callBack.onSuccess(responseDto.getData());
                        } else {
                            callBack.onError(responseDto.getMessage());
                        }
                    }
                });
    }
}
