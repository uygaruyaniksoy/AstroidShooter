package com.group6.frontend.controller;

import com.group6.frontend.Main;
import com.group6.frontend.model.entities.webConsumer.PlayerSigninDTO;
import com.group6.frontend.model.entities.webConsumer.PlayerSignupDTO;
import com.group6.frontend.model.enums.GameScreen;
import com.group6.frontend.util.ShowAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class SigninController {

    ShowAlert showAlertClass;
    private final Stage stage;
    private final String resourceUrl = "http://localhost:8080/";


    public SigninController(Stage stage) {
        this.stage = stage;
        showAlertClass = new ShowAlert();
    }

    public void signinSubmitHandler(GridPane gridPane,  PasswordField passwordField, TextField emailField) {
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();


        if (emailField.getText().isEmpty()) {
            showAlertClass.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your email id");

        }
        else if (passwordField.getText().isEmpty()) {
            showAlertClass.showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a password");

        } else {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            PlayerSigninDTO playerDTO = new PlayerSigninDTO(emailField.getText(),passwordField.getText());
            HttpEntity<PlayerSigninDTO> request = new HttpEntity<>(playerDTO,headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity response = restTemplate.postForEntity(
                    resourceUrl+"player/sign_in", request , String.class);

            if(response.getStatusCode() == HttpStatus.OK) {
                showAlertClass.showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Authentication Successful!", "Welcome ");
                stage.setScene(Main.getScenes().get(GameScreen.GAME));
            }

        }
    }

}
