package com.group6.frontend.view;

import com.group6.frontend.controller.MainMenuViewController;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;


public class MainMenuView extends AbstractView {
    private final MainMenuViewController controller;
    public MainMenuView(Stage stage) {
        super(stage);
        controller = new MainMenuViewController(stage);
    }

    @Override
    public Scene getScene() {
        Scene scene = super.getScene();

        BorderPane borderPane = new BorderPane();

        VBox vbox = new VBox(15);
        vbox.setAlignment(Pos.CENTER);

        Button startButton = new Button("Start Game");
        vbox.getChildren().add(startButton);
        startButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event2 -> controller.startGameHandler());

        Button leaderboardButton = new Button("LeaderBoard");
        vbox.getChildren().add(leaderboardButton);
        leaderboardButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event1 -> controller.openLaderBoardHandler());

        Button signoutButton = new Button("Sign out");
        vbox.getChildren().add(signoutButton);
        signoutButton.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> controller.signOutHandler());

        // bind to take available space
        borderPane.prefHeightProperty().bind(scene.heightProperty());
        borderPane.prefWidthProperty().bind(scene.widthProperty());

        borderPane.setCenter(vbox);

        Pane root = (Pane) scene.getRoot();

        root.getChildren().add(borderPane);

        return scene;
    }
}
