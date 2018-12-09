package com.group6.frontend.controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import com.group6.frontend.Main;
import com.group6.frontend.model.enums.GameScreen;

public class MainMenuViewController {

    private Stage stage;

    public MainMenuViewController(Stage stage) {
        this.stage = stage;
    }

    public void gameButtonHandler(MouseEvent mouseEvent) {
        stage.setScene(Main.getScenes().get(GameScreen.GAME));
    }
}
