package com.jsimplec.todolist.httpclient;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.model.ItemList;
import com.jsimplec.todolist.model.RestResponseDto;
import com.jsimplec.todolist.util.HttpUtils;
import com.jsimplec.todolist.util.constants.StaticConstants;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

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
                        Type collectionType = new TypeToken<RestResponseDto<List<ItemList>>>() {
                        }.getType();
                        RestResponseDto<List<ItemList>> responseDto = objectMapper.fromJson(responseBody, collectionType);
                        if (responseDto.getMessage().equals(REST_SUCCESS_MESSAGE)) {
                            callBack.onSuccess(responseDto.getData());
                        } else {
                            callBack.onError(responseDto.getMessage());
                        }
                    }
                });
    }
}
