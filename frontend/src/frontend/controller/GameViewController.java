package frontend.controller;

import frontend.model.entities.GameObject;
import frontend.model.entities.PlayerSpaceship;
import frontend.model.enums.AttackType;
import frontend.util.EnemySpawner;
import frontend.util.Scheduler;
import frontend.util.Spawner;
import frontend.util.Timer;
import frontend.view.FeedbackGradient;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameViewController {
    private Stage stage;
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

        setEnemySpawner();
        setUpdateGameObjects();
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
        timer = new Timer() {
            @Override
            public void update(double delta) {
                Pane pane = ((Pane) stage.getScene().getRoot());
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
                                pane.getChildren().remove(gameObject.getRootPane());
                            }
                            if (otherGameObject.isDead()) {
                                gameObjects.remove(otherGameObject);
                                pane.getChildren().remove(otherGameObject.getRootPane());
                            }
                        }
                    }
                }
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
