package com.group6.frontend.model.entities.webConsumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlayerAuthenticationDTO {
    private String token;

    public PlayerAuthenticationDTO() {

    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
