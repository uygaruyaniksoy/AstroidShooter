package com.group26.termproject;


import lombok.Data;

import javax.persistence.Entity;
import java.sql.Date;

@Data
@Entity
public class ScoreBoard {
    private Players player;
    private Date date_time;
    private int score;

    ScoreBoard(Players player,Date date_time, int score) {
        this.player = player;
        this.date_time = date_time;
        this.score = score;
    }
}
