package com.jsimplec.todolist.callback;

public interface ErrorCallBack extends SuccessErrorCallBack<String> {

    default void onSuccess(String response) {

    }

    void onError(String errorMessage);
}
