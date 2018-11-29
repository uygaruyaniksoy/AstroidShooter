package com.group6.termproject.dto;

import lombok.Data;

@Data
public class PlayerAuthenticationDTO {
    private String token;

    public PlayerAuthenticationDTO(String token) {
        this.token = token;
    }
}
