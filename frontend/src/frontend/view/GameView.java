package frontend.view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import frontend.controller.GameViewController;

public class GameView extends AbstractView {
    private GameViewController controller;

    public GameView(Stage stage) {
        super(stage);
        controller = new GameViewController(stage);
    }

    @Override
    public Scene getScene() {
        Pane pane = new Pane();
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());
        pane.setStyle("-fx-background-color: aqua");

        Button button = new Button("Start Game");
        button.translateXProperty().bind(stage.widthProperty().divide(2).subtract(button.widthProperty().divide(2)));
        button.translateYProperty().bind(stage.heightProperty().divide(2).subtract(button.heightProperty().divide(2)));

        button.addEventFilter(MouseEvent.MOUSE_CLICKED, controller::startGame);
        button.addEventFilter(MouseEvent.MOUSE_CLICKED, e -> {
            pane.getChildren().remove(button);
        });
        pane.getChildren().add(button);

        return new Scene(pane);
    }
}
