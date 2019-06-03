package com.group6.frontend.model.entities.webConsumer;

import lombok.Data;

@Data
public class ScoreBoardDTO {
    int score;
    public ScoreBoardDTO(int score) {
        this.score = score;
    }
}
