package com.group6.termproject.tables;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
public class ScoreBoard {
    private @Id @GeneratedValue Integer sid;
    @ManyToOne
    @JoinColumn(name = "pid",nullable = false)
    private Player player;
    private Date date;
    private int score;

    public ScoreBoard() {
    }

    public ScoreBoard(Player player, Date date, int score) {
        this.player = player;
        this.date = date;
        this.score = score;
    }
}
