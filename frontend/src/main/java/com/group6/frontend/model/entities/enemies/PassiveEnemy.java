package com.group6.frontend.model.entities.enemies;

import com.group6.frontend.model.entities.GameObject;
import com.group6.frontend.model.entities.Spaceship;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class PassiveEnemy extends Enemy implements Spaceship {

    public PassiveEnemy(Stage stage, double spawnLocation) {
        super(stage, 10);
        this.pane.setTranslateX(spawnLocation);
        this.pane.setTranslateY(-100);
    }

    public PassiveEnemy(Stage stage, double x, double y) {
        super(stage, 10);
        this.pane.setTranslateX(x);
        this.pane.setTranslateY(y);
    }

    @Override
    public void draw() {
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/ships.png")));
        image.setFitWidth(64);
        image.setFitHeight(50);
        image.setViewport(new Rectangle2D(64,72,128, 100));
        image.setRotate(180);
        this.pane.getChildren().add(image);

    }

    @Override
    public void update(double delta) {
        this.pane.setTranslateX(this.pane.getTranslateX());
        int speed = -60;
        this.pane.setTranslateY(this.pane.getTranslateY() - speed * delta);
        position.setX(this.pane.getTranslateX());
        position.setY(this.pane.getTranslateY());
    }

    @Override
    public GameObject attack() {
        return null;
    }

    @Override
    public boolean shouldAttack(double time) {
        return false;
    }
}
