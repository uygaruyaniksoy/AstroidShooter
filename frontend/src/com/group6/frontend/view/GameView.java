package com.group6.frontend.view;

import com.group6.frontend.controller.GameViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameView extends AbstractView {
    private GameViewController controller;

    public GameView(Stage stage) {
        super(stage);
        controller = new GameViewController(stage);
    }

    @Override
    public Scene getScene() {
        Pane pane = new Pane();
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());
        pane.setStyle("-fx-background-color: aqua");

        Button button = new Button("Start Game");
        button.translateXProperty().bind(stage.widthProperty().divide(2).subtract(button.widthProperty().divide(2)));
        button.translateYProperty().bind(stage.heightProperty().divide(2).subtract(button.heightProperty().divide(2)));

        button.addEventFilter(MouseEvent.MOUSE_CLICKED, controller::startGame);
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pane.getChildren().remove(button));
        pane.getChildren().add(button);

        Pane healthbar = new Pane();
        healthbar.setPrefWidth(25);
        healthbar.prefHeightProperty().bind(stage.heightProperty().divide(4));
        healthbar.setTranslateX(25);
        healthbar.translateYProperty().bind(stage.heightProperty().divide(4).multiply(3).subtract(25));

        healthbar.setStyle("-fx-background-color: red; " +
                "-fx-border-color: black; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 100%; " +
                "-fx-background-radius: 50px; " +
                "");

        pane.getChildren().add(healthbar);

        return new Scene(pane);
    }
}
