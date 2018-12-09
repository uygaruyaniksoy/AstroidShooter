package com.group6.frontend.view;

import com.group6.frontend.controller.MainMenuViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuView extends AbstractView {
    private MainMenuViewController controller;
    public MainMenuView(Stage stage) {
        super(stage);

        controller = new MainMenuViewController(stage);
    }

    @Override
    public Scene getScene() {
        Scene scene = super.getScene();
        Pane root = (Pane) scene.getRoot();
        Button gameButton = new Button("Game");
        gameButton.setOnMouseClicked(controller::gameButtonHandler);
        root.getChildren().add(gameButton);

        return scene;
    }
}
