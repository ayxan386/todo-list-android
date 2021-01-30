package com.jsimplec.todolist.model;

public class ErrorResponse {
    private String message;
    private String time;
    private int status;

    public ErrorResponse() {
    }

    public ErrorResponse(String message, String time, int status) {
        this.message = message;
        this.time = time;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public String getTime() {
        return time;
    }

    public int getStatus() {
        return status;
    }
}
