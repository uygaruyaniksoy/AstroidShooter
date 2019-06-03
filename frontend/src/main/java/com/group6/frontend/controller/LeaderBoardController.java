package com.group6.frontend.controller;

import com.group6.frontend.Main;
import com.group6.frontend.model.entities.webConsumer.LeaderBoardDTO;
import com.group6.frontend.model.enums.GameScreen;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;

public class LeaderBoardController {
    private final Stage stage;

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

        String resourceUrl = "http://localhost:8080/";
        ResponseEntity<ArrayList<LeaderBoardDTO>> response = restTemplate.exchange(resourceUrl +path, HttpMethod.GET,request, temp);

        if(response.getStatusCode() == HttpStatus.OK) {
            ArrayList<LeaderBoardDTO> body = response.getBody();
            if (body != null && body.size() >0) {
                System.out.println("username:" + body.get(0).getNickName());
                data.clear();
                data.addAll(body);
            }
        }
        else if(response.getStatusCode() == HttpStatus.NO_CONTENT) {

        }

    }

    public void back() {
        stage.setScene(Main.getScenes().get(GameScreen.MAIN_MENU));
    }



}
