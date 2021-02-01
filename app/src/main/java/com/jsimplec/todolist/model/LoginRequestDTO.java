package com.jsimplec.todolist.model;

public class LoginRequestDTO {
    private final String nickname;
    private final String password;

    public LoginRequestDTO(String nickname, String password) {
        this.nickname = nickname;
        this.password = password;
    }

    public String getNickname() {
        return nickname;
    }

    public String getPassword() {
        return password;
    }
}
