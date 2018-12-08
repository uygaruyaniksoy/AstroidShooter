package frontend.util;

import frontend.model.entities.GameObject;
import frontend.model.entities.enemies.PassiveEnemy;
import frontend.util.Scheduler;
import javafx.stage.Stage;

public class EnemySpawner implements Spawner {
    private Stage stage;
    private Scheduler spawnScheduler;

    public EnemySpawner(Stage stage) {
        this.stage = stage;

    }

    @Override
    public void setSpawnScheduler(Scheduler spawnScheduler) {
        this.spawnScheduler = spawnScheduler;
    }

    public Scheduler getSpawnScheduler() {
        return spawnScheduler;
    }

    @Override
    public GameObject checkAndSpawn(long t) {
        if (Math.sin(t / 1.2) < 0) return null;
        return new PassiveEnemy(stage, getSpawnLocation(t));
    }

    @Override
    public double getSpawnLocation(long t) {
        return stage.getScene().getWidth() * (Math.sin(t / 3.) + 1) * 0.5;
    }


}
