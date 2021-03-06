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

public class PlayerSpaceship extends GameObject implements Spaceship {
    private Scheduler shootScheduler;
    private double moveTargetX;
    private double moveTargetY;
    private double score;
    private double killCount;
    private double level;

    private Pane healthBar;
    private FeedbackGradient gradient;
    private int bulletShot = 0;

    public PlayerSpaceship(Stage stage) {
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
    public void intersect(GameObject gameObject) {
        super.intersect(gameObject);

        if (gameObject instanceof Enemy || (gameObject instanceof Ammunition && gameObject.getSource() instanceof Enemy)) {

            clashFeedback();
        }
    }

    private void clashFeedback() {
        PlayerSpaceship that = this;
        shake();
        redshift();
    }

    /**
     * gui shifts to a red color and back to transparent in 1 second
     */
    private void redshift() {
        PlayerSpaceship that = this;
        new Scheduler(0.01) {
            private double delay = 0;
            @Override
            public void execute() {
                delay += 0.01;
                if (this.delay > .5) {
                    this.stop();
                    new Scheduler(0.01) {
                        private double delay = 0;
                        @Override
                        public void execute() {
                            delay += 0.01;
                            if (this.delay > .5) {
                                this.stop();
                                return;
                            }
                            that.gradient.setColor(Color.RED.interpolate(Color.BLACK, delay * 2));
                        }
                    }.start();
                }
                that.gradient.setColor(Color.BLACK.interpolate(Color.RED, delay * 2));
            }
        }.start();
    }

    /**
     * shakes the stage for 1 second
     */
    private void shake() {
        PlayerSpaceship that = this;
        new Scheduler(0.01) {
            private double delay = 0;
            @Override
            public void execute() {
                delay += 0.01;
                if (this.delay > .5) {
                    this.stop();
                    new Scheduler(0.01) {
                        private double delay = 0;
                        @Override
                        public void execute() {
                            delay += 0.01;
                            if (this.delay > .5) {
                                this.stop();
                                return;
                            }
                            that.stage.getScene().getRoot().setTranslateY(Math.sin(Math.PI * delay * 8) * 5 * Math.sin(Math.PI * delay * 8));
                            that.stage.getScene().getRoot().setTranslateX(Math.sin(Math.PI * delay * 8) * 5 * Math.cos(Math.PI * delay * 8));
                        }
                    }.start();
                }
                that.stage.getScene().getRoot().setTranslateY(Math.sin(Math.PI * delay * 8) * 5 * Math.sin(Math.PI * delay * 8));
                that.stage.getScene().getRoot().setTranslateX(Math.sin(Math.PI * delay * 8) * 5 * Math.cos(Math.PI * delay * 8));
            }
        }.start();
    }

    @Override
    public void move(double toX, double toY) {
        this.moveTargetX = toX;
        this.moveTargetY = toY;
    }

    @Override
    public void update(double delta) {
        double fromX = this.pane.getTranslateX();
        double fromY = this.pane.getTranslateY();

        int speed = 10;
        double newX = fromX + (moveTargetX - fromX) * speed * delta;
        double newY = fromY + (moveTargetY - fromY) * speed * delta;

        position.setX(newX);
        position.setY(newY);

        this.pane.setTranslateX(newX);
        this.pane.setTranslateY(newY);
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

    public double getShootRate() {
        return 0.2;
    }

    public Background getHealthBar() {
        float healthRatio = 1 - ((float) curHealth) / maxHealth;
        return new Background(
                new BackgroundFill(
                        new LinearGradient(0, 0, 0, 1,true,
                                CycleMethod.NO_CYCLE,
                                new Stop(0, Color.BLACK),
                                new Stop(healthRatio - .2, Color.BLACK),
                                new Stop(healthRatio, Color.RED),
                                new Stop(1, Color.RED)),
                        new CornerRadii(50), Insets.EMPTY));
    }

    public double getScore() {
        return score;
    }

    public void addScore(double score) {
        this.score += score;
    }

    public double getLevel() {
        return level;
    }

    public void setLevel(double level) {
        this.level = level;
    }

    public boolean isAutoShooting() {
        boolean autoShooting = true;
        return autoShooting;
    }

    public void increateKillCount() {
        killCount++;
    }

    public double getKillCount() {
        return killCount;
    }

    public void resetKillCount() {
        killCount = 0;
    }
}
