package com.group6.frontend.util;

import com.group6.frontend.model.entities.PlayerSpaceship;
import com.group6.frontend.model.entities.enemies.Enemy;
import com.group6.frontend.model.entities.enemies.HeavyAttackEnemy;
import com.group6.frontend.model.entities.enemies.LightAttackEnemy;
import com.group6.frontend.model.entities.enemies.PassiveEnemy;
import javafx.stage.Stage;

public class EnemySpawner implements Spawner {
    private final Stage stage;
    private final PlayerSpaceship player;
    private Scheduler spawnScheduler;

    public EnemySpawner(Stage stage, PlayerSpaceship player) {
        this.stage = stage;
        this.player = player;
    }

    public Class getEnemyTypeByStageAndLevel(int stage) {
        if (stage == 0) {
            if (player.getLevel() == 1) return PassiveEnemy.class;
            if (player.getLevel() == 2) return PassiveEnemy.class;
            if (player.getLevel() == 3) return LightAttackEnemy.class;
        } else if (stage == 1) {
            if (player.getLevel() == 1) return PassiveEnemy.class;
            if (player.getLevel() == 2) return LightAttackEnemy.class;
            if (player.getLevel() == 3) return HeavyAttackEnemy.class;
        } else {
            if (player.getLevel() == 1) return LightAttackEnemy.class;
            if (player.getLevel() == 2) return HeavyAttackEnemy.class;
            if (player.getLevel() == 3) return HeavyAttackEnemy.class;
        }

        return PassiveEnemy.class;
    }

    @Override
    public void setSpawnScheduler(Scheduler spawnScheduler) {
        this.spawnScheduler = spawnScheduler;
    }

    public Scheduler getSpawnScheduler() {
        return spawnScheduler;
    }

    /**
     * Spawns the enemies according to a mathematical formula so it will generate same enemies at the same locations every
     * time you play so it's fair.
     *
     * @param t the time at you are querying the scheduler
     * @param enemyType what kind of an enemy you want to instantiate
     * @return created enemy, null if nothing is created
     */
    @Override
    public Enemy checkAndSpawn(long t, Class enemyType) {
        if ((Math.sin(t) - Math.min(Math.cos(t / 3. + Math.PI) * Math.sqrt(t / 10.), 0)) < 0) return null;
        try {
            return (Enemy) enemyType.getConstructor(Stage.class, double.class).newInstance(stage, getSpawnLocation(t));
        } catch (Exception e) {
            return null;
        }
    }

    @Override
    public double getSpawnLocation(long t) {
        return stage.getScene().getWidth() * (Math.sin(t / 3.) + 1) * 0.5;
    }


}
