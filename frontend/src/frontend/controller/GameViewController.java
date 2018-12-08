package frontend.controller;

import frontend.view.FeedbackGradient;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import frontend.model.entities.PlayerSpaceship;

public class GameViewController {
    private FeedbackGradient gradient;

    private Stage stage;

    public GameViewController(Stage stage) {
        this.stage = stage;
    }

    public void startGame(MouseEvent mouseEvent) {
        gradient = new FeedbackGradient(stage);
        PlayerSpaceship player = new PlayerSpaceship(stage);
    }

}
