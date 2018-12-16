package com.group6.frontend.controller;
import com.group6.frontend.Main;
import com.group6.frontend.model.entities.webConsumer.PlayerSigninDTO;
import com.group6.frontend.model.enums.GameScreen;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class MainMenuViewController {

    private final Stage stage;
    private final String resourceUrl = "http://localhost:8080/";


    public MainMenuViewController(Stage stage) {
        this.stage = stage;
    }


    public void startGameHandler(MouseEvent event) {
        stage.setScene(Main.getScenes().get(GameScreen.GAME));
    }


    public void openLaderBoardHandler(MouseEvent event) {
        stage.setScene(Main.getScenes().get(GameScreen.LEADERBOARD));
    }

    public void signOutHandler(MouseEvent event) {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-access-token",Main.TOKEN);

        HttpEntity<PlayerSigninDTO> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        ResponseEntity response = restTemplate.exchange(resourceUrl+"player/sign_out",HttpMethod.DELETE,request,String.class );

        if(response.getStatusCode() == HttpStatus.OK) {
            Main.TOKEN = null;
            stage.setScene(Main.getScenes().get(GameScreen.FORM));
        }
    }



}



