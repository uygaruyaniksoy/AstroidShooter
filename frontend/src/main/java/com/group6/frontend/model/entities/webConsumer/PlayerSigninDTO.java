package com.group6.frontend.model.entities.webConsumer;

import lombok.Data;

@Data
public class PlayerSignInDTO {
    private String email;
    private String password;

    public PlayerSignInDTO( String email, String password) {
        this.email = email;
        this.password = password;
    }
}