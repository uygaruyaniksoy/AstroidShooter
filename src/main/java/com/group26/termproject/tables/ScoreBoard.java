package com.group26.termproject.tables;

import lombok.Data;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Data
public class ScoreBoard {
    private @Id @GeneratedValue Integer sid;
    @ManyToOne
    @JoinColumn(name = "pid",nullable = false)
    private Player player;
    private Date date;
    private int score;

    ScoreBoard() {
        player = new Player();
        date = null;
        score = -1;
    }

    public ScoreBoard(Player player, Date date, int score) {
        this.player = player;
        this.date = date;
        this.score = score;
    }
}
