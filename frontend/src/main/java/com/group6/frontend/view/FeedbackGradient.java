package com.group6.frontend.view;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class FeedbackGradient {
    private final Stage stage;
    private final Pane pane;
    private Rectangle gradient;

    public FeedbackGradient(Stage stage) {
        this.stage = stage;
        this.pane = ((Pane) stage.getScene().getRoot());

        gradient = new Rectangle();
        gradient.widthProperty().bind(this.pane.widthProperty());
        gradient.heightProperty().bind(this.pane.heightProperty().divide(4));

        gradient.translateYProperty().bind(this.pane.heightProperty().divide(4).multiply(3));

        LinearGradient linearGradient = new LinearGradient(0, 0, 0, this.pane.heightProperty().getValue() / 2, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.TRANSPARENT),
                new Stop(1, Color.BLACK));
        gradient.setFill(linearGradient);
        this.pane.getChildren().add(gradient);
    }

    public void setColor(Color color) {
        LinearGradient linearGradient = new LinearGradient(0, 0, 0, this.pane.heightProperty().getValue() / 2, false, CycleMethod.NO_CYCLE,
                new Stop(0, Color.TRANSPARENT),
                new Stop(0.5, color),
                new Stop(1, color));
        gradient.setFill(linearGradient);
    }

}
