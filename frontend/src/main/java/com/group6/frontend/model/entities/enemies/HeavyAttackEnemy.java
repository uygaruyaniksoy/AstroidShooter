package com.group6.frontend.model.entities.enemies;

import com.group6.frontend.model.entities.GameObject;
import com.group6.frontend.model.entities.Spaceship;
import com.group6.frontend.model.entities.ammos.Rocket;
import com.group6.frontend.model.enums.AttackType;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class HeavyAttackEnemy extends Enemy implements Spaceship {
    private int speed = -60;
    private int bulletShot;

    public HeavyAttackEnemy(Stage stage, double spawnLocation) {
        super(stage, 40);
        this.pane.setTranslateX(spawnLocation);
        this.pane.setTranslateY(-100);
    }

    @Override
    public void draw() {
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/ships.png")));
        image.setFitWidth(64);
        image.setFitHeight(50);
        image.setViewport(new Rectangle2D(32,1050,224-32, 1220 - 1050));
        image.setRotate(180);
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
    public GameObject attack(double x, double y, AttackType attackType) {
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

    @Override
    public void updateAI() {

    }
}
