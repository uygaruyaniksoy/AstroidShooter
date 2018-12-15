package com.group6.frontend.model.entities.enemies;

import com.group6.frontend.model.entities.GameObject;
import com.group6.frontend.model.entities.Spaceship;
import javafx.stage.Stage;

public abstract class Enemy extends GameObject implements EnemyAI, Spaceship {
    public Enemy(Stage stage, int health) {
        super(stage, health);
    }

    @Override
    public boolean isDead() {
        return super.isDead() || (this.pane.getTranslateY() > this.stage.getHeight() + 50);
    }
}
