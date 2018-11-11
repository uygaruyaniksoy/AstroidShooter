package com.group26.termproject;


import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Data
public class Players {
    private @Id @GeneratedValue Long id;
    private String nick_name;
    private String email;
    private String password;

    Players(String nick_name, String email,String password ) {
        this.nick_name = nick_name;
        this.email = email;
        this.password = password;
    }
}
