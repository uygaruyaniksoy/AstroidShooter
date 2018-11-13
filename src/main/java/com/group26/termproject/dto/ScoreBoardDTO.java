package com.group26.termproject.dto;

import lombok.Data;

@Data
public class ScoreBoardDTO {
    int score;
    ScoreBoardDTO(int score) {
        this.score = score;
    }
}
