package com.group6.frontend.model.entities.ammos;

import com.group6.frontend.model.entities.GameObject;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class Rocket extends GameObject implements Ammunition {
    private ImageView image;

    public Rocket(Stage stage, GameObject source, double x, double y) {
        super(stage, 20);

        this.source = source;

        this.pane.setTranslateX(x);
        this.pane.setTranslateY(y);
    }

    @Override
    public void draw() {
        image = new ImageView(new Image(getClass().getResourceAsStream("/ships.png")));
        image.setFitWidth(28);
        image.setFitHeight(65);
        image.setViewport(new Rectangle2D(320,415,28, 65));
        this.pane.getChildren().add(image);
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
        if (speed < 0) {
            image.setRotate(180);
        }
    }

    @Override
    public boolean isDead() {
        return super.isDead() ||
                (this.pane.getTranslateY() + this.pane.getLayoutBounds().getHeight() < 0) ||
                (this.pane.getTranslateY() > this.stage.getHeight() + 50);
    }
}
