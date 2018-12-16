package com.group6.frontend.model.entities;

import com.group6.frontend.model.enums.AttackType;
import com.group6.frontend.util.Position;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public abstract class GameObject implements IGameObject {
    protected final Stage stage;
    protected final Pane pane;

    // bullet source
    protected GameObject source;

    int curHealth;
    final int maxHealth;
    protected int speed;
    private AttackType attackType;
    protected final Position position = new Position(0, 0);

    protected GameObject(Stage stage, int health) {
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

    /**
     * checks if 2 objects colludes and delivers damage to each one of them if they are colliding
     * @param gameObject the gameObject that will be checked.
     */
    @Override
    public void intersect(GameObject gameObject) {
        if (gameObject.source == this || this.source == gameObject) return;
        dealDamage(gameObject.getHealth());
        gameObject.dealDamage(curHealth);
        if (gameObject.source != null) gameObject.dealDamage(gameObject.getHealth());
        if (this.source != null) this.dealDamage(this.getHealth());
    }

    private int getHealth() {
        return curHealth;
    }

    public int getMaxHealth() {
        return maxHealth;
    }

    public boolean isDead() {
        return curHealth <= 0;
    }

    private void dealDamage(int damage) {
        curHealth -= damage;
    }

    public Position getPosition() {
        return position;
    }

    public GameObject getSource() {
        return source;
    }

    public AttackType getAttackType() {
        return attackType;
    }

}
