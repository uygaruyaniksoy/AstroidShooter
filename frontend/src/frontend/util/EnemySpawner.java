package frontend.controller;

import frontend.model.entities.GameObject;
import frontend.util.Scheduler;
import javafx.stage.Stage;

public class EnemySpawner {
    private Stage stage;
    private Scheduler spawnScheduler;

    public EnemySpawner(Stage stage) {
        this.stage = stage;

    }

    public void setSpawnScheduler(Scheduler spawnScheduler) {
        this.spawnScheduler = spawnScheduler;
    }
}
