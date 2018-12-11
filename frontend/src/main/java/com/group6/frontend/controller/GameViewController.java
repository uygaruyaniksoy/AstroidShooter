package com.group6.frontend.controller;

import com.group6.frontend.model.entities.GameObject;
import com.group6.frontend.model.entities.PlayerSpaceship;
import com.group6.frontend.util.EnemySpawner;
import com.group6.frontend.util.Scheduler;
import com.group6.frontend.util.Spawner;
import com.group6.frontend.util.Timer;
import com.group6.frontend.view.FeedbackGradient;
import com.group6.frontend.model.enums.AttackType;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameViewController {
    private Stage stage;
    private Pane pane;
    private Pane healthbar;
    private FeedbackGradient gradient;

    private Timer timer;

    private PlayerSpaceship player;
    private List<GameObject> gameObjects = new ArrayList<>();

    public GameViewController(Stage stage) {
        this.stage = stage;
    }

    public void startGame(MouseEvent mouseEvent) {
        gradient = new FeedbackGradient(stage);
        player = new PlayerSpaceship(stage);
        gameObjects.add(player);

        this.stage.getScene().onMouseMovedProperty().setValue(this::onMouseMoved);
        this.stage.getScene().onMouseDraggedProperty().setValue(this::onMouseDragged);
        this.stage.getScene().onMousePressedProperty().setValue(this::onMousePressed);
        this.stage.getScene().onMouseReleasedProperty().setValue(this::onMouseReleased);

        this.pane = ((Pane) stage.getScene().getRoot());

        setHealthBar();
        setEnemySpawner();
        setUpdateGameObjects();
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
        player.getShootScheduler().stop();
        player.setShootScheduler(null);
    }

    private void onMousePressed(MouseEvent mouseEvent) {
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

    private void setUpdateGameObjects() {
        GameViewController that = this;
        timer = new Timer() {
            @Override
            public void update(double delta) {
                for (int i = 0; i < gameObjects.size(); i++) {
                    GameObject gameObject = gameObjects.get(i);
                    gameObject.update(delta);
                    for (int j = i + 1; j < gameObjects.size(); j++) {
                        GameObject otherGameObject = gameObjects.get(j);
                        if (gameObject
                                .getRootPane()
                                .getBoundsInParent()
                                .intersects(otherGameObject.getRootPane().getBoundsInParent())) {
                            gameObject.intersect(otherGameObject);

                            if (gameObject.isDead()) {
                                gameObjects.remove(gameObject);
                                that.pane.getChildren().remove(gameObject.getRootPane());
                            }
                            if (otherGameObject.isDead()) {
                                gameObjects.remove(otherGameObject);
                                that.pane.getChildren().remove(otherGameObject.getRootPane());
                            }
                        }
                    }
                }


                that.healthbar.setBackground(player.getHealthBar());
            }
        };
        timer.start();
    }

    private void setEnemySpawner() {
        Spawner enemySpawner = new EnemySpawner(stage);
        enemySpawner.setSpawnScheduler(new Scheduler(0.5) {
            long sec = 0;
            @Override
            public void execute() {
                GameObject gameObject = enemySpawner.checkAndSpawn(sec++);
                if (gameObject != null) {
                    gameObjects.add(gameObject);
                }
            }
        });
        enemySpawner.getSpawnScheduler().start();
    }
}
