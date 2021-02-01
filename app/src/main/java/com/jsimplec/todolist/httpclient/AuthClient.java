package com.jsimplec.todolist.httpclient;

import android.util.Log;

import com.google.gson.Gson;
import com.jsimplec.todolist.callback.SuccessErrorCallBack;
import com.jsimplec.todolist.model.ErrorResponse;
import com.jsimplec.todolist.model.LoginRequestDTO;
import com.jsimplec.todolist.model.TokenResponseDTO;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.jsimplec.todolist.util.constants.StaticConstants.MS_BASE_URL;

public class AuthClient {

    public static final MediaType JSON
            = MediaType.get("application/json; charset=utf-8");
    public static final AuthClient AUTH_CLIENT = new AuthClient();
    private static final Gson gson = new Gson();
    private final OkHttpClient httpClient;

    private AuthClient() {
        httpClient = new OkHttpClient();
    }

    public static ErrorResponse getErrorResponse(Response response) throws IOException {
        return gson.fromJson(response.body().string(), ErrorResponse.class);
    }

    public void login(String username, String password, SuccessErrorCallBack<TokenResponseDTO> callBack) {
        LoginRequestDTO requestDTO = new LoginRequestDTO(username, password);
        Request request = new Request.Builder()
                .url(String.format("%s/auth/login", MS_BASE_URL))
                .post(RequestBody.create(gson.toJson(requestDTO).getBytes(), JSON))
                .build();
        httpClient.newCall(request)
                .enqueue(new Callback() {
                    @Override
                    public void onFailure(@NotNull Call call, @NotNull IOException e) {
                        Log.e("AuthClient", e.getMessage());
                        callBack.onError(e.getMessage());
                    }

                    @Override
                    public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                        int responseStatus = response.code();
                        if (responseStatus == 200) {
                            TokenResponseDTO responseDTO = gson.fromJson(response.body().string(), TokenResponseDTO.class);
                            callBack.onSuccess(responseDTO);
                        } else {
                            ErrorResponse errorResponse = getErrorResponse(response);
                            callBack.onError(errorResponse.getMessage());
                        }
                    }
                });
    }
}
