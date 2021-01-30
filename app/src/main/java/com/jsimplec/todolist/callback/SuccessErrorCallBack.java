package com.jsimplec.todolist.callback;

public interface SuccessErrorCallBack<T> {
    void onSuccess(T response);

    void onError(String errorMessage);
}
