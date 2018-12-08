package frontend.util;

import frontend.model.entities.GameObject;

public interface Spawner {
    void setSpawnScheduler(Scheduler spawnScheduler);
    Scheduler getSpawnScheduler();
    GameObject checkAndSpawn(long t);
    double getSpawnLocation(long t);
}
