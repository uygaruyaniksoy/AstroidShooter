package com.group6.frontend.model.entities.webConsumer;

import lombok.Data;

@Data
public class PlayerSigninDTO {
    private String email;
    private String password;

    public PlayerSigninDTO( String email, String password) {
        this.email = email;
        this.password = password;
    }
}