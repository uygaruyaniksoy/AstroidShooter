package com.group6.frontend.controller;
import com.group6.frontend.Main;
import com.group6.frontend.model.entities.webConsumer.PlayerSignInDTO;
import com.group6.frontend.model.enums.GameScreen;
import javafx.stage.Stage;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;


public class MainMenuViewController {

    private final Stage stage;


    public MainMenuViewController(Stage stage) {
        this.stage = stage;
    }


    public void startGameHandler() {
        stage.setScene(Main.getScenes().get(GameScreen.GAME));
    }


    public void openLaderBoardHandler() {
        stage.setScene(Main.getScenes().get(GameScreen.LEADERBOARD));
    }

    public void signOutHandler() {

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("x-access-token",Main.TOKEN);

        HttpEntity<PlayerSignInDTO> request = new HttpEntity<>(headers);

        RestTemplate restTemplate = new RestTemplate();
        String resourceUrl = "http://localhost:8080/";
        ResponseEntity response = restTemplate.exchange(resourceUrl +"player/sign_out",HttpMethod.DELETE,request,String.class );

        if(response.getStatusCode() == HttpStatus.OK) {
            Main.TOKEN = null;
            stage.setScene(Main.getScenes().get(GameScreen.FORM));
        }
    }
}



