package com.jsimplec.todolist.httpclient;

import android.util.Log;

import com.google.gson.Gson;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.model.LoginRequestDTO;
import com.jsimplec.todolist.model.TokenResponseDTO;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class AuthClient {

    private OkHttpClient httpClient;
    public static final AuthClient AUTH_CLIENT = new AuthClient();
    private final String baseUrl = "https://todo-list-2021.herokuapp.com";
    private final Gson gson;

    private AuthClient() {
        httpClient = new OkHttpClient();
        gson = new Gson();
    }

    public void login(String username, String password, SuccessErrorCallBack<TokenResponseDTO> callBack) {
        LoginRequestDTO requestDTO = new LoginRequestDTO(username, password);
        Request request = new Request.Builder()
                .url(String.format("%s/auth/login", baseUrl))
                .method("POST", RequestBody.create(gson.toJson(requestDTO).getBytes()))
                .build();
        try (Response response = httpClient.newCall(request).execute()) {
            TokenResponseDTO responseDTO = gson.fromJson(response.body().string(), TokenResponseDTO.class);
            callBack.onSuccess(responseDTO);
        } catch (IOException e) {
            Log.e("AuthClient", e.getMessage());
            callBack.onError(e.getMessage());
        }

    }
}
