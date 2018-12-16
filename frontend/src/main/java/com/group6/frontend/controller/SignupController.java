package com.group6.frontend.controller;

import com.group6.frontend.model.entities.webConsumer.PlayerSignupDTO;
import com.group6.frontend.util.ShowAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

public class SignupController {
    private final ShowAlert showAlertClass;


    public SignupController(Stage stage) {
        showAlertClass = new ShowAlert();
        Stage stage1 = stage;
    }

//    public void gameButtonHandler(MouseEvent mouseEvent) {
//        stage.setScene(Main.getScenes().get(GameScreen.GAME));
//    }

    public void signupSubmitHandler(Window window, TextField nameField, PasswordField passwordField, TextField emailField) {

        if (nameField.getText().isEmpty()) {
            showAlertClass.showAlert(Alert.AlertType.ERROR, window, "Form Error!", "Please enter your name");

        }
        else if (emailField.getText().isEmpty()) {
            showAlertClass.showAlert(Alert.AlertType.ERROR, window, "Form Error!", "Please enter your email id");

        }
        else if (passwordField.getText().isEmpty()) {
            showAlertClass.showAlert(Alert.AlertType.ERROR, window, "Form Error!", "Please enter a password");

        } else {

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            PlayerSignupDTO playerDTO = new PlayerSignupDTO(nameField.getText(),emailField.getText(),passwordField.getText());
            HttpEntity<PlayerSignupDTO> request = new HttpEntity<>(playerDTO,headers);

            RestTemplate restTemplate = new RestTemplate();
            String resourceUrl = "http://localhost:8080/";
            ResponseEntity response = restTemplate.postForEntity(
                    resourceUrl +"player/sign_up", request , String.class);

            if(response.getStatusCode() == HttpStatus.OK) {
                showAlertClass.showAlert(Alert.AlertType.CONFIRMATION, window, "Registration Successful!", "Welcome " + nameField.getText());
            }
            else if(response.getStatusCode() == HttpStatus.NO_CONTENT) {
                showAlertClass.showAlert(Alert.AlertType.ERROR, window, "Registration Failed!", "User already registered");
            }

        }
    }
}
