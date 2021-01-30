package com.jsimplec.todolist.model;

public class LoginRequestDTO {
    private String nickname;
    private String password;

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
