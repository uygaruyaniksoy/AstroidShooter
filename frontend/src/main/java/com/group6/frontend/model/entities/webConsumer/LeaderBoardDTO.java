package com.group6.frontend.model.entities.webConsumer;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class LeaderBoardDTO {
    private String nickName;
    private int score;

    public LeaderBoardDTO() {}

    public LeaderBoardDTO(String username, int score) {
        this.nickName = username;
        this.score = score;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String username) {
        this.nickName = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
