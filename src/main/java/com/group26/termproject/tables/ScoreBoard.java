package com.group26.termproject.tables;


import com.group26.termproject.tables.Player;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.sql.Date;

@Data
@Entity
public class ScoreBoard {
    private @Id @GeneratedValue Long id;
    private Long playerId;
    private Date date_time;
    private int score;

    public ScoreBoard() {
    }

    ScoreBoard(Player player, Date date_time, int score) {
        this.playerId = player.getId();
        this.date_time = date_time;
        this.score = score;
    }
}
