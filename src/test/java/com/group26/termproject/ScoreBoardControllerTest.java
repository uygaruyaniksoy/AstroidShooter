package com.group26.termproject;

import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.group26.termproject.controllers.ScoreBoardController;
import com.group26.termproject.dto.LeaderBoardDTO;
import com.group26.termproject.dto.PlayerAuthenticationDTO;
import com.group26.termproject.dto.ScoreBoardDTO;
import com.group26.termproject.repositories.PlayerRepository;
import com.group26.termproject.repositories.ScoreBoardRepository;
import com.group26.termproject.tables.Player;
import com.group26.termproject.tables.ScoreBoard;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.util.*;

@RunWith( SpringJUnit4ClassRunner.class )
@WebMvcTest(ScoreBoardController.class)
public class ScoreBoardControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private ScoreBoardRepository scoreBoardRepository;
    @MockBean
    private PlayerRepository playerRepository;
    @MockBean
    private ScoreBoardController scoreBoardController;


    private ObjectMapper mapper;

    @Before
    public void setup() {

    }

    //scoreboard/daily, scoreboard, scoreboard/weekly will generally be tested together since their implementation is very similar

    @Test
    public void shouldReturnNoContentResponse() throws Exception{

        List<LeaderBoardDTO> arrayList = new ArrayList<>();
        ResponseEntity<List<LeaderBoardDTO>> responseEntity =
                new ResponseEntity<>(arrayList,HttpStatus.NO_CONTENT);

        when(scoreBoardController.scoreboard()).thenReturn(responseEntity);

        mockMvc.perform(get("/scoreboard")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        when(scoreBoardController.daily()).thenReturn(responseEntity);

        mockMvc.perform(get("/scoreboard/daily"))
                .andExpect(status().isNoContent());

        when(scoreBoardController.weekly()).thenReturn(responseEntity);

        mockMvc.perform(get("/scoreboard/weekly"))
                .andExpect(status().isNoContent());
    }



    @Test
    public void scoreShouldbeSavedTypeCheck() throws Exception {
        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.setPlayer(new Player("nick","mail","2"));
        scoreBoard.setDate(new Date());
        scoreBoard.setScore(5);

        when(scoreBoardRepository.save(any(ScoreBoard.class))).thenReturn(scoreBoard);

        Assert.assertEquals(scoreBoardRepository.save(scoreBoard),scoreBoard);
    }

    @Test
    public void functionShouldReturnSameValueWithGetRequest() throws Exception {

        ScoreBoard scoreBoard = new ScoreBoard();
        scoreBoard.setPlayer(new Player("nick","mail","2"));
        scoreBoard.setDate(new Date());
        scoreBoard.setScore(5);

        ArrayList<LeaderBoardDTO> list = new ArrayList<>();
        list.add(new LeaderBoardDTO(scoreBoard.getPlayer().getNickName(),scoreBoard.getScore()));

        ResponseEntity<List<LeaderBoardDTO>> responseEntity = new ResponseEntity<>(list, HttpStatus.OK);
        when(scoreBoardController.scoreboard()).thenReturn(responseEntity);

        this.mockMvc.perform(get("/scoreboard")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickName" ,is("nick")));


        //daily
        when(scoreBoardController.daily()).thenReturn(responseEntity);

        this.mockMvc.perform(get("/scoreboard/daily")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickName" ,is("nick")));


        //weekly
        when(scoreBoardController.weekly()).thenReturn(responseEntity);

        this.mockMvc.perform(get("/scoreboard/weekly")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].nickName" ,is("nick")));
    }



    @Test
    public void shouldNotPostScoreServiceIfTokenIsInvalid() throws Exception {

        ScoreBoardDTO scoreBoardDTO = new ScoreBoardDTO(5);

        PlayerAuthenticationDTO playerAuthenticationDTO = new PlayerAuthenticationDTO("");

        when(scoreBoardController.postScore(playerAuthenticationDTO,scoreBoardDTO)).thenReturn(null);

        mockMvc.perform(get("/scoreboard/update").contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());

        verify(scoreBoardRepository,never()).save(any(ScoreBoard.class));

    }


}
