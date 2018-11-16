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


    /**
     * This service can be used without authenntication.
     *
     * When the client calls this service, he/she can see the leaderboard of all times.
     *
     * @return ResponseEntity that includes followings:
     *
     * -> http request status (204 if there is no score uploaded,
     * 200 if there is at least one player and one score uploaded by player.)
     *
     * -> A list of Leaderboard Data Transfer Objects which includes the nick names of the players and the their maximum scores
     *
     */

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

    /**
     * This service can be used without authenntication.
     *
     * When the client calls this service, he/she can see the leaderboard of that day.
     *
     * @return ResponseEntity that includes followings:
     *
     * -> http request status (204 if there is no score uploaded on that day,
     * 200 if there is at least one player and one score uploaded by player on that day.)
     *
     * -> A list of Leaderboard Data Transfer Objects which includes the nick names of the players
     * and the their maximum scores that are taken on that day.
     *
     */

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

    /**
     * This service can be used without authenntication.
     *
     * When the client calls this service, he/she can see the leaderboard of that week.
     *
     * @return ResponseEntity that includes followings:
     *
     * -> http request status (204 if there is no score uploaded on that week,
     * 200 if there is at least one player and one score uploaded by player on that week.)
     *
     * -> A list of Leaderboard Data Transfer Objects which includes the nick names of the players
     * and the their maximum scores that are taken on that week.
     *
     */

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

    /**
     * This service can be used needs authenntication.
     *
     * When the client calls this service, he/she can upload the score to the database.
     *
     *
     * @param playerAuthenticationDTO Service needs the authentication token in the header of the request
     *                                as a value of "x-access-token" to recognize the player
     *
     * @param scoreBoardDTO Service also needs score value to save. Score value must be greater than 0.
     *
     * @return ResponseEntity that includes followings:
     *
     * -> http request status (415 if authenticaiton token is invalid,
     * 200 if there is at least one player and one score uploaded by player on that week.)
     *
     * -> The score value given by user.
     *
     */

    @PostMapping(path="/scoreboard/update",consumes= MediaType.APPLICATION_JSON_VALUE, produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ScoreBoardDTO> postScore(@RequestHeader("x-access-token") PlayerAuthenticationDTO playerAuthenticationDTO,
                         @RequestBody ScoreBoardDTO scoreBoardDTO) {

        GregorianCalendar g = new GregorianCalendar();
        Date date = new Date(g.getTime().getTime());

        String token = playerAuthenticationDTO.getToken();
        Optional<Player> player = getPlayer(token);

        if(player!=null && player.isPresent()) {
            ScoreBoard scoreBoard = scoreBoardRepository.save(new ScoreBoard(player.get(),date,scoreBoardDTO.getScore()));
            if(scoreBoard != null && scoreBoardDTO.getScore() > 0)  {
                return new ResponseEntity<>(scoreBoardDTO,HttpStatus.OK);
            }
        }
        return null;
    }
}