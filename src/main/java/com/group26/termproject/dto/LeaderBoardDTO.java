package com.group26.termproject.dto;

import lombok.Data;

@Data
public class LeaderBoardDTO {

    private String nickName;
    private int score;

    public LeaderBoardDTO(String nickName, int score) {
        this.nickName = nickName;
        this.score = score;
    }

}
