package com.group26.termproject.dto;

import lombok.Data;

@Data
public class ScoreBoardDTO {
    Integer id;
    int score;

    ScoreBoardDTO(int id,int score) {
        this.id = id;
        this.score = score;
    }
}
