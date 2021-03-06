package com.group6.frontend.util;

import com.group6.frontend.model.entities.GameObject;

interface Spawner {
    void setSpawnScheduler(Scheduler spawnScheduler);
    Scheduler getSpawnScheduler();
    GameObject checkAndSpawn(long t, Class passiveEnemyClass);
    double getSpawnLocation(long t);
}
