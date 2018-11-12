package com.group26.termproject.controllers;


import com.group26.termproject.dto.LeaderBoardDTO;
import com.group26.termproject.dto.PlayerAuthenticationDTO;
import com.group26.termproject.dto.ScoreBoardDTO;
import com.group26.termproject.repositories.PlayerRepository;
import com.group26.termproject.repositories.ScoreBoardRepository;
import com.group26.termproject.tables.Player;
import com.group26.termproject.tables.ScoreBoard;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.sql.Date;
import java.util.*;

@RestController
public class ScoreBoardController {
    private final ScoreBoardRepository scoreBoardRepository;

    ScoreBoardController(ScoreBoardRepository repository) {
        this.scoreBoardRepository = repository;
    }

    @GetMapping("/scoreboard/daily")
    ResponseEntity<List<LeaderBoardDTO>> daily() {

        List<Tuple> tuple_list = scoreBoardRepository.getDailyLeaderboard();
        List<LeaderBoardDTO> res = new ArrayList<>();

        for(Tuple el:tuple_list) {
            LeaderBoardDTO temp = new LeaderBoardDTO((String) el.get(0),(int)el.get(1));
            res.add(temp);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);

    }

    @GetMapping("/scoreboard/weekly")
    ResponseEntity<List<LeaderBoardDTO>> weekly() {

        List<Tuple> tuple_list = scoreBoardRepository.getWeeklyLeaderboard();
        List<LeaderBoardDTO> res = new ArrayList<>();

        for(Tuple el:tuple_list) {
            LeaderBoardDTO temp = new LeaderBoardDTO((String) el.get(0),(int)el.get(1));
            res.add(temp);
        }
        return new ResponseEntity<>(res, HttpStatus.OK);
    }

    @PostMapping(path="/scoreboard/{id}")
    ScoreBoard postScore(@RequestHeader("x-access-token") PlayerAuthenticationDTO playerAuthenticationDTO,
                         @RequestBody ScoreBoardDTO scoreBoardDTO) {

        Date date = new Date(System.currentTimeMillis());
        PlayerRestController playerRestController = new PlayerRestController();

        int id = scoreBoardDTO.getId();
        String token = playerAuthenticationDTO.getToken();

        //Check for authentication token
        if(token.equals(playerRestController.getToken(id))) {
            Optional<Player> player = playerRestController.getPlayer(id);


            if(player.isPresent()) {
                return scoreBoardRepository.save(new ScoreBoard(player.get(),date,scoreBoardDTO.getScore()));
            }
        }
        return null;
    }


}