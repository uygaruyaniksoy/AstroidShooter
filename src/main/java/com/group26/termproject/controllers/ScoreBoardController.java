package com.group26.termproject.controllers;

import com.group26.termproject.dto.LeaderBoardDTO;
import com.group26.termproject.dto.PlayerAuthenticationDTO;
import com.group26.termproject.dto.ScoreBoardDTO;
import com.group26.termproject.repositories.PlayerRepository;
import com.group26.termproject.repositories.ScoreBoardRepository;
import com.group26.termproject.tables.Player;
import com.group26.termproject.tables.ScoreBoard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
import java.sql.Date;
import java.util.*;

@RestController
public class ScoreBoardController {
    @Autowired
    private final ScoreBoardRepository scoreBoardRepository;
    @Autowired
    private final PlayerRepository playerRepository;

    ScoreBoardController(ScoreBoardRepository repository, PlayerRepository playerRepository) {
        this.scoreBoardRepository = repository;
        this.playerRepository = playerRepository;
    }


    Optional<Player> getPlayer(String token) {

        if(playerRepository == null){
            System.out.println("here");
            return null;
        }

        Optional<Player> p = playerRepository.findByToken(token);
        if(p!=null) {
            return p;
        }

        return null;

    }

    @GetMapping("/scoreboard")
    ResponseEntity<List<LeaderBoardDTO>> scoreboard() {

        List<Tuple> tuple_list = scoreBoardRepository.getLeaderboard();
        List<LeaderBoardDTO> res = new ArrayList<>();

        for(Tuple el:tuple_list) {
            LeaderBoardDTO temp = new LeaderBoardDTO((String) el.get(0),(int)el.get(1));
            res.add(temp);
        }

        return new ResponseEntity<>(res, HttpStatus.OK);
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


    @PostMapping("/testing")
    int f(@RequestBody int a) {
        return a+3;
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

    @PostMapping(path="/scoreboard/update")
    ResponseEntity<ScoreBoardDTO> postScore(@RequestHeader("x-access-token") PlayerAuthenticationDTO playerAuthenticationDTO,
                         @RequestBody ScoreBoardDTO scoreBoardDTO) {

        Date date = new Date(System.currentTimeMillis());

        String token = playerAuthenticationDTO.getToken();

        Optional<Player> player = getPlayer(token);

        if(player!=null && player.isPresent()) {
            ScoreBoard scoreBoard = scoreBoardRepository.save(new ScoreBoard(player.get(),date,scoreBoardDTO.getScore()));
            if(scoreBoard != null) {
                return new ResponseEntity<>(scoreBoardDTO,HttpStatus.OK);
            }
        }
        return null;
    }
}