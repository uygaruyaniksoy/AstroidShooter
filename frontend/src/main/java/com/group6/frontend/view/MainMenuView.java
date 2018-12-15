package com.group6.frontend.view;

import com.group6.frontend.controller.MainMenuViewController;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MainMenuView extends AbstractView {
    private MainMenuViewController controller;
    public MainMenuView(Stage stage) {
        super(stage);
        controller = new MainMenuViewController();
    }

    @Override
    public Scene getScene() {
        Scene scene = super.getScene();




        return scene;
    }
}
