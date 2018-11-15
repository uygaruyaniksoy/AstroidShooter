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
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.Tuple;
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
            return null;
        }

        Optional<Player> p = playerRepository.findByToken(token);
        if(p!=null) {
            return p;
        }
        return null;
    }


    @GetMapping(path="/scoreboard",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LeaderBoardDTO>> scoreboard() {
        List<Tuple> tuple_list = scoreBoardRepository.getLeaderboard();
        List<LeaderBoardDTO> res = new ArrayList<>();

        for(Tuple el:tuple_list) {
            LeaderBoardDTO temp = new LeaderBoardDTO((String) el.get(0),(int)el.get(1));
            res.add(temp);
        }

        if(tuple_list.size() == 0) {
            return new ResponseEntity<>(res,HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }


    }

    @GetMapping(path="/scoreboard/daily",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LeaderBoardDTO>> daily() {

        Optional<List<Tuple>> tuple_list = Optional.ofNullable(scoreBoardRepository.getDailyLeaderboard());
        List<LeaderBoardDTO> res = new ArrayList<>();

        if(tuple_list.isPresent() && tuple_list.get().size() != 0) {
            for(Tuple el:tuple_list.get()) {
                LeaderBoardDTO temp = new LeaderBoardDTO((String) el.get(0),(int)el.get(1));
                res.add(temp);
            }
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
        else {
            return new ResponseEntity<>(res, HttpStatus.NO_CONTENT);
        }
    }


    @GetMapping(path="/scoreboard/weekly",produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LeaderBoardDTO>> weekly() {

        List<Tuple> tuple_list = scoreBoardRepository.getWeeklyLeaderboard();
        List<LeaderBoardDTO> res = new ArrayList<>();

        for(Tuple el:tuple_list) {
            LeaderBoardDTO temp = new LeaderBoardDTO((String) el.get(0),(int)el.get(1));
            res.add(temp);
        }

        if(tuple_list.size() == 0) {
            return new ResponseEntity<>(res,HttpStatus.NO_CONTENT);
        }
        else {
            return new ResponseEntity<>(res, HttpStatus.OK);
        }
    }

    @PostMapping(path="/scoreboard/update",consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScoreBoardDTO> postScore(@RequestHeader("x-access-token") PlayerAuthenticationDTO playerAuthenticationDTO,
                         @RequestBody ScoreBoardDTO scoreBoardDTO) {

        GregorianCalendar g = new GregorianCalendar();
        Date date = new Date(g.getTime().getTime());

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