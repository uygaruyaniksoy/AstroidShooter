package com.group6.termproject.dto;

import lombok.Data;

@Data
public class PlayerChangePasswordDTO {
    private String token;
    private String password;
}
