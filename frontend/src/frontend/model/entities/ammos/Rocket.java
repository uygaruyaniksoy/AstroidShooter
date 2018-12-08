package frontend.model.entities.ammos;

import frontend.model.entities.AbstractGameObject;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Rocket extends AbstractGameObject implements Ammunition {
    public Rocket(Stage stage, double x, double y) {
        super(stage);

        this.pane.setTranslateX(x);
        this.pane.setTranslateY(y);
    }

    @Override
    public void draw() {
        int bulletWidth = 10;
        int bulletHeight = 25;

        Rectangle rect = new Rectangle();
        rect.setWidth(bulletWidth);
        rect.setHeight(bulletHeight);
        rect.setFill(Color.DARKORCHID);

        this.pane.getChildren().add(rect);
    }

    @Override
    public boolean moveTo(double x, double y, double rate) {
        return false;
    }
}
