package com.group6.frontend.model.entities.webConsumer;

import lombok.Data;

@Data
public class PlayerSignupDTO {
    private String nickName;
    private String email;
    private String password;

    public PlayerSignupDTO(String nickName, String email, String password) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }
}
