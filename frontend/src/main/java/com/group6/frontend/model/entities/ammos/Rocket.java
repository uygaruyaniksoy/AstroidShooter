package com.group6.frontend.model.entities.ammos;

import com.group6.frontend.model.entities.GameObject;
import javafx.geometry.Bounds;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Rocket extends GameObject implements Ammunition {
    public Rocket(Stage stage, GameObject source, double x, double y) {
        super(stage, 20);

        this.source = source;

        this.pane.setTranslateX(x);
        this.pane.setTranslateY(y);
    }

    @Override
    public void draw() {
        int bulletWidth = 10;
        int bulletHeight = 25;

        Rectangle rect = new Rectangle();
        rect.setWidth(bulletWidth);
        rect.setHeight(bulletHeight);
        rect.setFill(Color.DARKORCHID);

        this.pane.getChildren().add(rect);
    }

    @Override
    public void update(double delta) {
        this.pane.setTranslateX(this.pane.getTranslateX());
        this.pane.setTranslateY(this.pane.getTranslateY() - speed * delta);
        position.setX(this.pane.getTranslateX());
        position.setY(this.pane.getTranslateY());
    }

    @Override
    public void setAmmoSpeed(int speed) {
        this.speed = speed;
    }

    @Override
    public boolean isDead() {
        return super.isDead() ||
                (this.pane.getTranslateY() + this.pane.getLayoutBounds().getHeight() < 0);
    }
}
