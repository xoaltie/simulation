package com.github.xoaltie.simulation.entity;

import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.path.PathFinder;

public abstract class Creature extends Entity {
    private final int speed;
    private final PathFinder pathFinder;
    private int health;

    public Creature(int health, int speed, PathFinder pathFinder) {
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
