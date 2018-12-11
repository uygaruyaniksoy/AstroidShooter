package com.group6.frontend.model.entities;

import com.group6.frontend.util.Position;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class GameObject implements IGameObject {
    protected Stage stage;
    protected Pane pane;

    // bullet source
    protected GameObject source;

    protected int curHealth;
    protected int maxHealth;
    protected Position position = new Position(0, 0);

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
        if (gameObject.source != null) gameObject.dealDamage(gameObject.getHealth());
        if (this.source != null) this.dealDamage(this.getHealth());
    }

    public int getHealth() {
        return curHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public void setHealth(int curHealth) {
        this.curHealth = curHealth;
    }

    public boolean isDead() {
        return curHealth <= 0;
    }

    public void dealDamage(int damage) {
        curHealth -= damage;
    }

    public Position getPosition() {
        return position;
    }

    public GameObject getSource() {
        return source;
    }
}
