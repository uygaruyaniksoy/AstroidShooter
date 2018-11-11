package com.group26.termproject.tables;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
public class Authentication {
    private @Id @GeneratedValue Long id;
    private String token;
    private Long playerId;

    public Authentication() {
    }

    public Authentication(String token, Long playerId) {
        this.token = token;
        this.playerId = playerId;
    }
}

