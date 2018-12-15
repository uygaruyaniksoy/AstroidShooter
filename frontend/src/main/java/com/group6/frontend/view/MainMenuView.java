package com.group6.frontend.view;

import com.group6.frontend.controller.MainMenuViewController;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
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

        BorderPane borderPane = new BorderPane();

        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start Game");
        vbox.getChildren().add(startButton);

        Button leaderboardButton = new Button("LeaderBoard");
        vbox.getChildren().add(leaderboardButton);

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(vbox);

        Pane root = (Pane) scene.getRoot();

        root.getChildren().add(borderPane);


        return scene;
    }
}
