package com.group6.frontend.controller;

import com.group6.frontend.Main;
import com.group6.frontend.model.entities.webConsumer.PlayerAuthenticationDTO;
import com.group6.frontend.model.entities.webConsumer.PlayerSigninDTO;
import com.group6.frontend.model.enums.GameScreen;
import com.group6.frontend.util.ShowAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class SigninController {

    ShowAlert showAlertClass;
    private final Stage stage;
    private final String resourceUrl = "http://localhost:8080/";


    public SigninController(Stage stage) {
        this.stage = stage;
        showAlertClass = new ShowAlert();
    }

    public void signinSubmitHandler(Window window, PasswordField passwordField, TextField emailField) {

        if (emailField.getText().isEmpty()) {
            showAlertClass.showAlert(Alert.AlertType.ERROR, window, "Form Error!", "Please enter your email id");

        }
        else if (passwordField.getText().isEmpty()) {
            showAlertClass.showAlert(Alert.AlertType.ERROR, window, "Form Error!", "Please enter a password");
        }
        else {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            PlayerSigninDTO playerDTO = new PlayerSigninDTO();
            playerDTO.setEmail(emailField.getText());
            playerDTO.setPassword(passwordField.getText());

            HttpEntity<PlayerSigninDTO> request = new HttpEntity<>(playerDTO,headers);

            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<PlayerAuthenticationDTO> response = restTemplate.exchange(
                    resourceUrl+"player/sign_in",HttpMethod.POST ,request , PlayerAuthenticationDTO.class);

            if(response.getStatusCode() == HttpStatus.NO_CONTENT) {
                showAlertClass.showAlert(Alert.AlertType.ERROR, window, "Form Error!", "Please enter your email and password correctly");
            }
            else if(response.getStatusCode() == HttpStatus.OK) {
                Main.TOKEN = response.getBody().getToken();
                showAlertClass.showAlert(Alert.AlertType.CONFIRMATION, window, "Authentication Successful!", "Welcome ");
                stage.setScene(Main.getScenes().get(GameScreen.MAIN_MENU));
            }


        }
    }

}
