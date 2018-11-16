package com.group26.termproject.tables;

import lombok.Data;

import javax.persistence.*;

@Data
@Entity
public class Authentication {
    private @Id @GeneratedValue Long id;
    private String token;
    @OneToOne
    @JoinColumn(name = "pid",nullable = true)
    private Player player;

    public Authentication() {
    }

    public Authentication(String token, Player player) {
        this.token = token;
        this.player = player;
    }
}

