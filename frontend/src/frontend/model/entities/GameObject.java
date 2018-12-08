package frontend.model.entities;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class GameObject implements IGameObject {
    protected Stage stage;
    protected Pane pane;

    // bullet source
    protected GameObject source;

    protected int curHealth;
    protected int maxHealth;

    public GameObject(Stage stage, int health) {
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

    @Override
    public Pane getRootPane() {
        return this.pane;
    }

    @Override
    public void intersect(GameObject gameObject) {
        if (gameObject.source == this || this.source == gameObject) return;
        dealDamage(gameObject.getHealth());
        gameObject.dealDamage(curHealth);
    }

    public int getHealth() {
        return curHealth;
    }

    public void setHealth(int curHealth) {
        this.curHealth = curHealth;
    }

    public boolean isDead() {
        return curHealth < 0;
    }

    public void dealDamage(int damage) {
        curHealth -= damage;
    }
}
