package frontend.util;

import javafx.animation.AnimationTimer;

public abstract class Scheduler extends AnimationTimer {
    private final long interval;
    private long lastExecution = 0;

    protected Scheduler(double intervalSeconds) {
        this.interval = (long) (intervalSeconds * 1_000_000_000.0);
    }

    @Override
    public void handle(long now) {
        if (now - lastExecution < interval) {
            return;
        }

        lastExecution = now;
        execute();
    }

    abstract public void execute();
}
