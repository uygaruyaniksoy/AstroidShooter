package com.group6.frontend.controller;

import com.group6.frontend.model.entities.GameObject;
import com.group6.frontend.model.entities.PlayerSpaceship;
import com.group6.frontend.model.entities.ammos.Ammunition;
import com.group6.frontend.model.entities.enemies.Enemy;
import com.group6.frontend.model.enums.AttackType;
import com.group6.frontend.util.EnemySpawner;
import com.group6.frontend.util.Scheduler;
import com.group6.frontend.util.StringResources;
import com.group6.frontend.util.Timer;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class GameViewController extends Timer {
    private Stage stage;
    private Pane pane;
    private Pane healthbar;
    private Text score;
    private Text timeView;

    private PlayerSpaceship player;
    private List<GameObject> gameObjects = new ArrayList<>();
    private List<Enemy> enemies = new ArrayList<>();
    private EnemySpawner enemySpawner;
    private int count;
    private double time = 60.0;

    public GameViewController(Stage stage) {
        this.stage = stage;
    }

    public void startGame(MouseEvent mouseEvent) {
        player = new PlayerSpaceship(stage);
        gameObjects.add(player);

        this.stage.getScene().onMouseMovedProperty().setValue(this::onMouseMoved);
        this.stage.getScene().onMouseDraggedProperty().setValue(this::onMouseDragged);
        this.stage.getScene().onMousePressedProperty().setValue(this::onMousePressed);
        this.stage.getScene().onMouseReleasedProperty().setValue(this::onMouseReleased);

        this.pane = ((Pane) stage.getScene().getRoot());

        setHealthBar();
        startEnemySpawner();
        setPlayerShootingScheduler();
        initScoreTextAndTime();

        player.setLevel(1);

        start(); // start timer so that every frame update function will be called
    }

    private void initScoreTextAndTime() {
        score = new Text("Score: " + (((int) player.getScore())));
        score.setFont(Font.font("Verdana", FontWeight.BOLD,36));
        score.setFill(Color.DARKORCHID);
        score.setStrokeWidth(3);
        score.setStroke(Color.BLACK);
        score.setTextAlignment(TextAlignment.LEFT);

        score.translateXProperty().set(20);
        score.translateYProperty().set(80);
        pane.getChildren().add(score);

        timeView = new Text("Score: " + time);
        timeView.setFont(Font.font("Verdana", FontWeight.BOLD,36));
        timeView.setFill(Color.DARKORCHID);
        timeView.setStrokeWidth(3);
        timeView.setStroke(Color.BLACK);
        timeView.setTextAlignment(TextAlignment.LEFT);

        timeView.translateXProperty().set(20);
        timeView.translateYProperty().set(40);
        pane.getChildren().add(timeView);
    }

    private void setPlayerShootingScheduler() {
        player.setShootScheduler(new Scheduler(player.getShootRate()) {
            @Override
            public void execute() {
                gameObjects.add(player.attack(
                        player.getPosition().getX(),
                        player.getPosition().getY(),
                        AttackType.LIGHT));
            }
        });
        player.getShootScheduler().start();
    }

    private void setHealthBar() {
        this.healthbar = new Pane();
        healthbar.setPrefWidth(25);
        healthbar.prefHeightProperty().bind(stage.heightProperty().divide(4));
        healthbar.setTranslateX(25);
        healthbar.translateYProperty().bind(stage.heightProperty().divide(4).multiply(3).subtract(25));

        healthbar.setBorder(new Border(
                new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID,
                        new CornerRadii(100),
                        new BorderWidths(2))));

        healthbar.setBackground(player.getHealthBar());

        this.pane.getChildren().add(healthbar);
    }

    private void onMouseReleased(MouseEvent mouseEvent) {
        if (player.isAutoShooting()) return;
        player.getShootScheduler().stop();
        player.setShootScheduler(null);
    }

    private void onMousePressed(MouseEvent mouseEvent) {
        if (player.isAutoShooting()) return;
        player.setShootScheduler(new Scheduler(player.getShootRate()) {
            @Override
            public void execute() {
                gameObjects.add(player.attack(mouseEvent.getX(), mouseEvent.getY(), AttackType.LIGHT));
            }
        });
        player.getShootScheduler().start();
    }

    private void onMouseDragged(MouseEvent mouseEvent) {
        player.move(mouseEvent.getX(), mouseEvent.getY());
    }

    private void onMouseMoved(MouseEvent mouseEvent) {
        player.move(mouseEvent.getX(), mouseEvent.getY());
    }

    private void startEnemySpawner() {

        enemySpawner = new EnemySpawner(stage, player);
        enemySpawner.setSpawnScheduler(new Scheduler(0.5) {
            @Override
            public void execute() {
//                System.out.println(count);
                Enemy enemy;
//                if (player.getKillCount() > 0) {
                if (player.getKillCount() > 25) {
                    enemy = enemySpawner.checkAndSpawn(count*3, enemySpawner.getEnemyTypeByStageAndLevel(1));
                    if (enemy != null) {
                        gameObjects.add(enemy);
                        enemies.add(enemy);
                    }
                }
//                if (player.getKillCount() > 0) {
                if (player.getKillCount() > 100) {
                    enemy = enemySpawner.checkAndSpawn(count*2, enemySpawner.getEnemyTypeByStageAndLevel(2));
                    if (enemy != null) {
                        gameObjects.add(enemy);
                        enemies.add(enemy);
                    }
                }
                enemy = enemySpawner.checkAndSpawn(count++, enemySpawner.getEnemyTypeByStageAndLevel(0));
                if (enemy != null) {
                    gameObjects.add(enemy);
                    enemies.add(enemy);
                }
            }
        });
        enemySpawner.getSpawnScheduler().start();
    }

    @Override
    public void update(double delta) {
        System.out.println(delta);
        System.out.println(gameObjects.size());
        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject = gameObjects.get(i);
            gameObject.update(delta);
            for (int j = i + 1; j < gameObjects.size(); j++) {
                GameObject otherGameObject = gameObjects.get(j);
                if (gameObject
                        .getRootPane()
                        .getBoundsInParent()
                        .intersects(otherGameObject.getRootPane().getBoundsInParent()) &&
                        !((gameObject instanceof Enemy && otherGameObject instanceof Ammunition && otherGameObject.getSource() instanceof Enemy) ||
                        (otherGameObject instanceof Enemy && gameObject instanceof Ammunition && gameObject.getSource() instanceof Enemy) ||
                        (gameObject instanceof Enemy && otherGameObject instanceof Enemy))
                ) {
                    gameObject.intersect(otherGameObject);

                    checkKill(otherGameObject, gameObject);
                    checkKill(gameObject, otherGameObject);
                }
            }
        }

        for (Enemy enemy : enemies) {
            if (enemy.shouldAttack(count)) {
                GameObject ammo = enemy.attack(enemy.getPosition().getX(), enemy.getPosition().getY() - 10, enemy.getAttackType());
                if (ammo != null) gameObjects.add(ammo);
            }
        }

        for (int i = 0; i < gameObjects.size(); i++) {
            GameObject gameObject = gameObjects.get(i);
            if (gameObject.isDead()) {
                gameObjects.remove(gameObject);
                if (gameObject instanceof Enemy) enemies.remove(gameObject);
                this.pane.getChildren().remove(gameObject.getRootPane());
                i--;
            }
        }

        this.healthbar.setBackground(player.getHealthBar());
        this.score.setText("Score: " + (int) player.getScore());
        this.timeView.setText("Time: " + new DecimalFormat("##,##").format(time));

        time -= delta;

        if (player.isDead()) {
            finish();
        } else if (time < 0) {
            endLevel();
        }
    }

    private void endLevel() {
        if (player.getLevel() == 3) {
            gameOver();
        }
        GameViewController that = this;
        this.pane.getChildren().removeIf(node -> node != that.player.getRootPane());
        enemies.clear();
        gameObjects.clear();
        gameObjects.add(player);
        player.setLevel(player.getLevel() + 1);

        player.getShootScheduler().stop();
        enemySpawner.getSpawnScheduler().stop();
        stop();

        Text text = new Text("Get ready for next level");
        text.translateXProperty().bind(stage.widthProperty().divide(2).subtract(text.getLayoutBounds().getWidth()));
        text.translateYProperty().bind(stage.heightProperty().divide(2).subtract(20));

        Button nextLevel = new Button("next level");
        nextLevel.translateXProperty().bind(stage.widthProperty().divide(2).subtract(nextLevel.getLayoutBounds().getWidth()));
        nextLevel.translateYProperty().bind(stage.heightProperty().divide(2));
        nextLevel.setOnMouseClicked(mouseEvent -> {
            player.getShootScheduler().start();
            enemySpawner.getSpawnScheduler().start();
            that.start();
            that.time = 60;

            that.pane.getChildren().remove(nextLevel);
            that.pane.getChildren().remove(text);

            that.setHealthBar();
            that.enemySpawner.getSpawnScheduler().start();
            that.player.getShootScheduler().start();
            that.initScoreTextAndTime();
            that.player.drawGradient();

            that.count = 0;
            that.player.resetKillCount();
        });

        this.pane.getChildren().add(text);
        this.pane.getChildren().add(nextLevel);
    }

    private void gameOver() {
        System.out.println("gameover");
    }

    private void checkKill(GameObject gameObject, GameObject otherGameObject) {
        if (otherGameObject.isDead() && gameObject.getSource() == player) {
            player.addScore(otherGameObject.getMaxHealth());
            player.increateKillCount();
        }
    }

    private void finish() {
        // stop the timer and end the game
        stop();

        Text endGameText = new Text(StringResources.getEndGameText());
        endGameText.setFont(Font.font("Verdana", FontWeight.BOLD,48));
        endGameText.setFill(Color.DARKORCHID);
        endGameText.setStrokeWidth(3);
        endGameText.setStroke(Color.BLACK);

        Bounds bounds = endGameText.getLayoutBounds();
        endGameText.translateXProperty().bind(stage.widthProperty().subtract(bounds.getWidth()).divide(2));
        endGameText.translateYProperty().bind(stage.heightProperty().subtract(bounds.getHeight()).divide(2));
        pane.getChildren().add(endGameText);

        Text scoreText = new Text("Score: " + (((int) player.getScore())));
        scoreText.setFont(Font.font("Verdana", FontWeight.BOLD,36));
        scoreText.setFill(Color.DARKORCHID);
        scoreText.setStrokeWidth(3);
        scoreText.setStroke(Color.BLACK);

        bounds = scoreText.getLayoutBounds();
        scoreText.translateXProperty().bind(stage.widthProperty().subtract(bounds.getWidth()).divide(2));
        scoreText.translateYProperty().bind(stage.heightProperty().subtract(bounds.getHeight()).divide(1.66));
        pane.getChildren().add(scoreText);

        System.out.println("fin");

        this.stage.getScene().onMouseMovedProperty().setValue(null);
        this.stage.getScene().onMouseDraggedProperty().setValue(null);
        this.stage.getScene().onMousePressedProperty().setValue(null);
        this.stage.getScene().onMouseReleasedProperty().setValue(null);
    }
}
