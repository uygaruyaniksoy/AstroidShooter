package frontend.controller;

import frontend.model.entities.GameObject;
import frontend.model.entities.PlayerSpaceship;
import frontend.model.enums.AttackType;
import frontend.util.Scheduler;
import frontend.util.Timer;
import frontend.view.FeedbackGradient;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameViewController {
    private Stage stage;
    private FeedbackGradient gradient;

    private MouseEvent mouseEvent;
    private Timer timer;

    private PlayerSpaceship player;
    private List<GameObject> gameObjects = new ArrayList<>();

    public GameViewController(Stage stage) {
        this.stage = stage;
    }

    public void startGame(MouseEvent mouseEvent) {
        gradient = new FeedbackGradient(stage);
        player = new PlayerSpaceship(stage);

        this.stage.getScene().onMouseMovedProperty().setValue(this::onMouseMoved);
        this.stage.getScene().onMouseDraggedProperty().setValue(this::onMouseDragged);
        this.stage.getScene().onMousePressedProperty().setValue(this::onMousePressed);
        this.stage.getScene().onMouseReleasedProperty().setValue(this::onMouseReleased);
    }

    private void onMouseReleased(MouseEvent mouseEvent) {
        player.getShootScheduler().stop();
        player.setShootScheduler(null);
    }

    private void onMousePressed(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        player.setShootScheduler(new Scheduler(player.getShootRate()) {
            @Override
            public void execute() {
                gameObjects.add(player.attack(mouseEvent.getX(), mouseEvent.getY(), AttackType.LIGHT));
            }
        });
        player.getShootScheduler().start();
    }

    private void onMouseDragged(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        onMouseMoved(mouseEvent);
    }

    private void onMouseMoved(MouseEvent mouseEvent) {
        this.mouseEvent = mouseEvent;
        if (timer != null) return;
        GameViewController that = this;
        timer = new Timer() {
            @Override
            public void update(double delta) {
                if (player.moveTo(that.mouseEvent.getX(), that.mouseEvent.getY(), delta)) {
                    that.timer.stop();
                    that.timer = null;
                }
            }
        };
        timer.start();
    }
}
