package com.group6.frontend.model.entities.enemies;

import com.group6.frontend.model.entities.GameObject;
import com.group6.frontend.model.entities.Spaceship;
import com.group6.frontend.model.entities.ammos.Rocket;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class BossEnemy extends Enemy implements Spaceship {
    private int bulletShot;

    public BossEnemy(Stage stage, double spawnLocation) {
        super(stage, 1000);
        this.pane.setTranslateX(spawnLocation);
        this.pane.setTranslateY(-300);
    }

    @Override
    public void draw() {
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/ships.png")));
        image.setFitWidth(350);
        image.setFitHeight(250);
        image.setViewport(new Rectangle2D(28,2596,227-28, 2859 - 2596));
        image.setRotate(180);
        this.pane.getChildren().add(image);
    }

    @Override
    public void update(double delta) {
        if (pane.getTranslateY() > 150) return;
        this.pane.setTranslateX(this.pane.getTranslateX());
        int speed = -20;
        this.pane.setTranslateY(this.pane.getTranslateY() - speed * delta);
        position.setX(this.pane.getTranslateX());
        position.setY(this.pane.getTranslateY());
    }

    @Override
    public GameObject attack() {
        if (pane.getTranslateY() < 0) return null;
        int centerX = 40;
        int centerY = 25;
        int bulletOffset = 45;

        int gunIndex = (bulletShot++ % 2) * 2 - 1;

        Rocket rocket = new Rocket(stage, this,
                pane.getTranslateX() + pane.getLayoutX() + centerX + bulletOffset * gunIndex,
                pane.getTranslateY() + pane.getLayoutY() + centerY * 2);
        rocket.setAmmoSpeed(-400);
        return rocket;
    }

    @Override
    public boolean shouldAttack(double time) {
        if (this.lastAttackTime != time && Math.floor(Math.random() * 1000) == 0) {
            lastAttackTime = time;
            return true;
        }
        return false;
    }

}
