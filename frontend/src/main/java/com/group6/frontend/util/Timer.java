package com.group6.frontend.util;

import javafx.animation.AnimationTimer;

public abstract class Timer extends AnimationTimer {
    private long lastUpdate = 0;

    @Override
    public void handle(long now) {
        if (lastUpdate == 0) {
            lastUpdate = now;
            return;
        }

        long timeElapsedNanoSeconds = now - lastUpdate;
        double timeElapsedSeconds = timeElapsedNanoSeconds / 1_000_000_000.0;
        lastUpdate = now;
        update(timeElapsedSeconds);
    }

    protected abstract void update(double delta);
}
