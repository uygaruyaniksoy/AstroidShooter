package com.group6.frontend.model.entities;

import com.group6.frontend.model.entities.ammos.Ammunition;
import com.group6.frontend.model.entities.ammos.Rocket;
import com.group6.frontend.model.entities.enemies.Enemy;
import com.group6.frontend.model.entities.enemies.EnemyAI;
import com.group6.frontend.model.enums.AttackType;
import com.group6.frontend.util.Position;
import com.group6.frontend.util.Scheduler;
import com.group6.frontend.util.Timer;
import com.group6.frontend.view.FeedbackGradient;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class PlayerSpaceship extends GameObject implements Spaceship {
    private int speed = 10;
    private double shootRate = 0.1;
    private Scheduler shootScheduler;
    private double moveTargetX;
    private double moveTargetY;
    private double score;
    private double killCount;
    private double level;
    private boolean autoShooting = true;

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
        int centerX = 40;
        int centerY = 25;
        int hullRadius = 15;
        int bulletWidth = 10;
        int bulletHeight = 25;
        int bulletOffset = 30;

        Rectangle axle = new Rectangle();
        axle.setHeight(10);
        axle.setWidth(centerX * 2);
        axle.setY(centerY * 2);
        axle.setFill(Color.BLACK);

        Rectangle bulletLeft = new Rectangle();
        bulletLeft.setHeight(bulletHeight);
        bulletLeft.setWidth(bulletWidth);
        bulletLeft.setY(centerY * 2 - bulletHeight / 2.0);
        bulletLeft.setX(centerX - bulletOffset- bulletWidth / 2.0);
        bulletLeft.setFill(Color.DARKORCHID);

        Rectangle bulletRight = new Rectangle();
        bulletRight.setHeight(bulletHeight);
        bulletRight.setWidth(bulletWidth);
        bulletRight.setY(centerY * 2 - bulletHeight / 2.0);
        bulletRight.setX(centerX + bulletOffset - bulletWidth / 2.0);
        bulletRight.setFill(Color.DARKORCHID);

        Circle hull = new Circle();
        hull.setCenterX(centerX);
        hull.setCenterY(hullRadius);
        hull.setRadius(hullRadius);
        hull.setFill(Color.DARKBLUE);

        Rectangle body = new Rectangle();
        body.setHeight(50);
        body.setWidth(hullRadius * 2);
        body.setY(hullRadius);
        body.setX(centerX - hullRadius);
        body.setFill(Color.DARKBLUE);

        this.pane.getChildren().add(axle);
        this.pane.getChildren().add(bulletLeft);
        this.pane.getChildren().add(bulletRight);
        this.pane.getChildren().add(body);
        this.pane.getChildren().add(hull);
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

        double newX = fromX + (moveTargetX - fromX) * this.speed * delta;
        double newY = fromY + (moveTargetY - fromY) * this.speed * delta;

        position.setX(newX);
        position.setY(newY);

        this.pane.setTranslateX(newX);
        this.pane.setTranslateY(newY);
    }

    @Override
    public GameObject attack(double x, double y, AttackType attackType) {
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
        return shootRate;
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
