package frontend.controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import frontend.model.entities.PlayerSpaceship;

public class GameViewController {

    private Stage stage;

    public GameViewController(Stage stage) {
        this.stage = stage;
    }

    public void startGame(MouseEvent mouseEvent) {
        PlayerSpaceship player = new PlayerSpaceship(stage);
    }

}
