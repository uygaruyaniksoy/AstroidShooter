package frontend.view;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class AbstractView implements IView {
    protected Stage stage;

    public AbstractView(Stage stage) {
        this.stage = stage;
    }

    @Override
    public Scene getScene() {
        Pane pane = new Pane();
        pane.prefWidthProperty().bind(stage.widthProperty());
        pane.prefHeightProperty().bind(stage.heightProperty());
        Scene scene = new Scene(pane);
        scene.setOnKeyTyped(keyEvent -> {
            if (keyEvent.getCharacter().equals("q")) {
                System.exit(0);
            }
        });
        return scene;
    }
}
