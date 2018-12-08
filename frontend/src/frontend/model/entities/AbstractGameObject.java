package frontend.model.entities;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class AbstractGameObject implements GameObject {
    protected Stage stage;
    protected Pane pane;

    protected int curHealth;
    protected int maxHealth;

    public AbstractGameObject(Stage stage, int health) {
        this.stage = stage;
        Pane pane = (Pane) this.stage.getScene().getRoot();
        this.pane = new Pane();
        pane.getChildren().add(this.pane);

        curHealth = health;
        maxHealth = health;

        // center the pane around spaceship
        this.pane.layoutXProperty().bind(this.pane.widthProperty().divide(-2));
        this.pane.layoutYProperty().bind(this.pane.heightProperty().divide(-2));
        draw();
    }

    @Override
    public void update(double delta) {

    }

    @Override
    public void move(double x, double y) {

    }
}
