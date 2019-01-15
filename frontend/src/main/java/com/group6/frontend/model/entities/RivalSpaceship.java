package com.group6.frontend.model.entities;

import com.group6.frontend.model.entities.ammos.Ammunition;
import com.group6.frontend.model.entities.ammos.Rocket;
import com.group6.frontend.model.entities.enemies.Enemy;
import com.group6.frontend.util.Scheduler;
import com.group6.frontend.view.FeedbackGradient;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

public class RivalSpaceship extends GameObject implements Spaceship {
    private Scheduler shootScheduler;
    private double score;
    private double killCount;
    private double level;

    private Pane healthBar;
    private FeedbackGradient gradient;
    private int bulletShot = 0;

    public RivalSpaceship(Stage stage) {
        super(stage, 100);
        drawGradient();
    }

    public void drawGradient() {
        gradient = new FeedbackGradient(stage);
    }

    @Override
    public void draw() {
        ImageView image = new ImageView(new Image(getClass().getResourceAsStream("/ships.png")));
        image.setFitWidth(80);
        image.setFitHeight(104);
        image.setViewport(new Rectangle2D(80,1580,96, 104));
        this.pane.getChildren().add(image);
    }

    @Override
    public GameObject attack() {
        int centerX = 40;
        int centerY = 25;
        int bulletOffset = 30;

        int gunIndex = (bulletShot++ % 2) * 2 - 1;

        Rocket rocket = new Rocket(stage, this,
                pane.getTranslateX() + pane.getLayoutX() + centerX + bulletOffset * gunIndex,
                pane.getTranslateY() + pane.getLayoutY() + centerY * 2);
        rocket.setAmmoSpeed(500);
        return rocket;
    }

    public Scheduler getShootScheduler() {
        return shootScheduler;
    }
    public void setShootScheduler(Scheduler shootScheduler) {
        this.shootScheduler = shootScheduler;
    }

    @Override
    public void move(double x, double y) {
        super.move(x, y);
        pane.setTranslateX(x);
        pane.setTranslateY(y);
    }
}
