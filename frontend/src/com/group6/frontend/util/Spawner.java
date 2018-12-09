package com.group6.frontend.util;

import com.group6.frontend.model.entities.GameObject;

public interface Spawner {
    void setSpawnScheduler(Scheduler spawnScheduler);
    Scheduler getSpawnScheduler();
    GameObject checkAndSpawn(long t);
    double getSpawnLocation(long t);
}
