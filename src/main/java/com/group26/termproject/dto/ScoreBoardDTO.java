package com.group26.termproject.dto;

import lombok.Data;

@Data
public class ScoreBoardDTO {
    int score;
    public ScoreBoardDTO(int score) {
        this.score = score;
    }
}
