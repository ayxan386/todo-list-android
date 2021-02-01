package com.jsimplec.todolist.util;

public class HttpUtils {
    public static String makeAuthHeader(String token) {
        return String.format("Bearer %s", token);
    }
}
