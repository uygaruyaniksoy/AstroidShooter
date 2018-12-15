package com.group6.frontend.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import com.group6.frontend.Main;
import com.group6.frontend.model.enums.GameScreen;
import javafx.stage.Window;

public class MainMenuViewController {

    private Stage stage;

    public MainMenuViewController(Stage stage) {
        this.stage = stage;
    }

    public void gameButtonHandler(MouseEvent mouseEvent) {
        stage.setScene(Main.getScenes().get(GameScreen.GAME));
    }

    public void signupSubmitHandler(GridPane gridPane, TextField nameField, PasswordField passwordField, TextField emailField) {
        if (nameField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your name");
            return;
        }
        else if (emailField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter your email id");
            return;
        }
        else if (passwordField.getText().isEmpty()) {
            showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error!", "Please enter a password");
            return;
        } else {

            showAlert(Alert.AlertType.CONFIRMATION, gridPane.getScene().getWindow(), "Registration Successful!", "Welcome " + nameField.getText());
        }
    }
    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

}



