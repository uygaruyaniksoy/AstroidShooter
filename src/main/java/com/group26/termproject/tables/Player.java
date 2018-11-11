package com.group26.termproject.tables;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Player {
    private @Id @GeneratedValue Long id;
    private String nickName;
    private String email;
    private String password;

    public Player() {
    }

    public Player(String nickName, String email, String password) {
        this.nickName = nickName;
        this.email = email;
        this.password = password;
    }
}
