package frontend.model.entities.ammos;

import frontend.model.entities.AbstractGameObject;
import frontend.util.Timer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

public class Rocket extends AbstractGameObject implements Ammunition {
    private int speed = 500;

    public Rocket(Stage stage, double x, double y) {
        super(stage, 20);

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
    public void update(double delta) {
        this.pane.setTranslateX(this.pane.getTranslateX());
        this.pane.setTranslateY(this.pane.getTranslateY() - speed * delta);
    }
}
