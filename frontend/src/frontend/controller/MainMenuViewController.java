package frontend.controller;

import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import frontend.Main;
import frontend.model.enums.GameScreen;

public class MainMenuViewController {

    private Stage stage;

    public MainMenuViewController(Stage stage) {
        this.stage = stage;
    }

    public void gameButtonHandler(MouseEvent mouseEvent) {
        stage.setScene(Main.getScenes().get(GameScreen.GAME));
    }
}
