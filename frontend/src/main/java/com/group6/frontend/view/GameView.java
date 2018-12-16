package com.group6.frontend.view;

import com.group6.frontend.controller.GameViewController;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GameView extends AbstractView {
    private GameViewController controller;

    public GameView(Stage stage) {
        super(stage);
        controller = new GameViewController(stage);
    }

    public GameViewController getController() {
        return controller;
    }

    @Override
    public Scene getScene() {
        Pane pane = new Pane();
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());


        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/space.jpg")));
        image.fitWidthProperty().bind(stage.widthProperty());
        image.fitHeightProperty().bind(stage.heightProperty());
        pane.getChildren().add(image);

        Button button = new Button("Start Game");
        button.translateXProperty().bind(stage.widthProperty().divide(2).subtract(button.widthProperty().divide(2)));
        button.translateYProperty().bind(stage.heightProperty().divide(2).subtract(button.heightProperty().divide(2)));

        button.addEventFilter(MouseEvent.MOUSE_CLICKED, controller::startGame);
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> pane.getChildren().remove(button));
        pane.getChildren().add(button);

        return new Scene(pane);
    }
}
