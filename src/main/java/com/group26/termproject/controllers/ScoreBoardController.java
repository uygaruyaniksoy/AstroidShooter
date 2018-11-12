package com.group26.termproject.controller;


import com.group26.termproject.dto.LeaderBoardDTO;
import com.group26.termproject.repositories.ScoreBoardRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.Tuple;
import java.util.*;

@RestController
public class ScoreBoardController {
    private final ScoreBoardRepository repository;


    ScoreBoardController(ScoreBoardRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/scoreboard/daily")
    ResponseEntity<List<LeaderBoardDTO>> daily() {

        List<Tuple> tuple_list = repository.getDailyLeaderboard();
        List<LeaderBoardDTO> res = new ArrayList<>();

        for(Tuple el:tuple_list) {
            LeaderBoardDTO temp = new LeaderBoardDTO((String) el.get(0),(int)el.get(1));
            res.add(temp);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/scoreboard/weekly")
    ResponseEntity<List<LeaderBoardDTO>> weekly() {

        List<Tuple> tuple_list = repository.getWeeklyLeaderboard();
        List<LeaderBoardDTO> res = new ArrayList<>();

        for(Tuple el:tuple_list) {
            LeaderBoardDTO temp = new LeaderBoardDTO((String) el.get(0),(int)el.get(1));
            res.add(temp);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }


}