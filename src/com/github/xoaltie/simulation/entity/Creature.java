package com.github.xoaltie.simulation.entity;

import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.path.PathFinder;

import java.util.Objects;

public abstract class Creature extends Entity {
    private final int speed;
    private final PathFinder pathFinder;
    private int health;

    public Creature(int health, int speed, PathFinder pathFinder) {
        Objects.requireNonNull(pathFinder);
        if (health <= 0) {
            throw new IllegalArgumentException("Здоровье существа не может быть %d!".formatted(health));
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("Скорость существа не может быть %d!".formatted(speed));
        }

        this.health = health;
        this.speed = speed;
        this.pathFinder = pathFinder;
    }

    public abstract void makeMove(WorldMap map);

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }
}
