package com.group6.frontend.view;

import com.group6.frontend.controller.GameViewController;
import com.group6.frontend.controller.MultiPlayerViewController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class MultiPlayerView extends AbstractView {
    private final MultiPlayerViewController controller;

    public MultiPlayerView(Stage stage) {
        super(stage);
        controller = new MultiPlayerViewController(stage);
    }

    public MultiPlayerViewController getController() {
        return controller;
    }

    @Override
    public Scene getScene() {
        Pane pane = new Pane();
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());

        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/multi.jpg")));
        image.fitWidthProperty().bind(stage.widthProperty());
        image.fitHeightProperty().bind(stage.heightProperty());
        pane.getChildren().add(image);

        return new Scene(pane);
    }
}
