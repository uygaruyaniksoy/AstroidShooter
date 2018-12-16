package com.group6.frontend.controller;

import com.group6.frontend.Main;
import com.group6.frontend.model.entities.webConsumer.LeaderBoardDTO;
import com.group6.frontend.model.entities.webConsumer.PlayerSignInDTO;
import com.group6.frontend.model.enums.GameScreen;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class LeaderBoardController {
    Stage stage;
    private final String resourceUrl = "http://localhost:8080/";
    public LeaderBoardController(Stage stage) {
        this.stage = stage;
    }

    public void tabChangedHandler(ObservableList<LeaderBoardDTO> data, String path) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-access-token", Main.TOKEN);

        HttpEntity<LeaderBoardDTO> request = new HttpEntity<>(headers);

        ParameterizedTypeReference<ArrayList<LeaderBoardDTO>> temp = new ParameterizedTypeReference<ArrayList<LeaderBoardDTO>>(){};

        RestTemplate restTemplate = new RestTemplate();

        ResponseEntity<ArrayList<LeaderBoardDTO>> response = restTemplate.exchange(resourceUrl+path, HttpMethod.GET,request,
                temp);

        if(response.getStatusCode() == HttpStatus.OK) {
            ArrayList<LeaderBoardDTO> body = response.getBody();
            data.addAll(body);
        }
        else if(response.getStatusCode() == HttpStatus.NO_CONTENT) {

        }

    }



}
