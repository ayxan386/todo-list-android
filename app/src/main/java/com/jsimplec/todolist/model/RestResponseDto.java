package com.jsimplec.todolist.model;

public class RestResponseDto<T> {
    private final String message;
    private final T data;

    public RestResponseDto(String message, T data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }
}
