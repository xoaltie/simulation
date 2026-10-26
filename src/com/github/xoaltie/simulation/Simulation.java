package com.github.xoaltie.simulation;

import com.github.xoaltie.simulation.action.Action;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.render.MapRenderer;

import java.util.List;
import java.util.Objects;

public final class Simulation {
    private final Object lock = new Object();
    private final WorldMap map;
    private final MapRenderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private int turnCounter;
    private volatile boolean isRunning = false;
    private volatile boolean isPaused = false;

    public Simulation(WorldMap map, MapRenderer renderer, List<Action> initActions, List<Action> turnActions) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(renderer);
        Objects.requireNonNull(initActions);
        Objects.requireNonNull(turnActions);

        this.map = map;
        this.renderer = renderer;
        this.initActions = initActions;
        this.turnActions = turnActions;
    }

    public void start() {
        reset();
        performActions(initActions);

        Thread thread = new Thread(() -> {
            while (isRunning) {
                synchronized (lock) {
                    while (isPaused) {
                        try {
                            lock.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                }

                if (!isRunning) {
                    break;
                }

                nextTurn();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        thread.start();
    }

    public void nextTurn() {
        turnCounter++;
        renderer.render(map, turnCounter);
        performActions(turnActions);
    }

    public void pause() {
        synchronized (lock) {
            isPaused = true;
        }
    }

    public void resume() {
        synchronized (lock) {
            isPaused = false;
            lock.notify();
        }
    }

    public void stop() {
        synchronized (lock) {
            isRunning = false;
            isPaused = false;
            lock.notify();
        }
    }

    private void reset() {
        turnCounter = 0;
        isRunning = true;
        isPaused = false;
        map.clear();
    }

    private void performActions(List<Action> actions) {
        for (Action action : actions) {
            action.execute(map);
        }
    }
}
