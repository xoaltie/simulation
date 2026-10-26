package com.github.xoaltie.simulation.entity;

import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.path.PathFinder;

import java.util.List;
import java.util.Objects;

public abstract sealed class Creature extends Entity permits Herbivore, Predator {
    private final int speed;
    private final PathFinder pathFinder;
    private final Class<? extends Entity> targetType;
    private int health;

    public Creature(int health, int speed, PathFinder pathFinder, Class<? extends Entity> targetType) {
        Objects.requireNonNull(pathFinder);
        Objects.requireNonNull(targetType);
        if (health <= 0) {
            throw new IllegalArgumentException("Здоровье существа не может быть %d!".formatted(health));
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("Скорость существа не может быть %d!".formatted(speed));
        }

        this.health = health;
        this.speed = speed;
        this.pathFinder = pathFinder;
        this.targetType = targetType;
    }

    public void makeMove(WorldMap map) {
        Objects.requireNonNull(map);

        Coordinates current = map.getCoordinates(this);
        List<Coordinates> path = getPathFinder().find(map, current, targetType);
        if (path.isEmpty()) {
            return;
        }

        for (int i = 0; i < getSpeed(); i++) {
            int nextCellIndex = Math.min(i, path.size() - 1);
            Coordinates nextCell = path.get(nextCellIndex);
            if (map.isCellEmpty(nextCell)) {
                map.moveEntity(current, nextCell);
                current = nextCell;
                continue;
            }
            Entity target = map.getEntity(nextCell).orElseThrow();
            boolean isTargetDead = interact(map, nextCell, target);
            if (isTargetDead) {
                map.moveEntity(current, nextCell);
                return;
            }
        }
    }

    public boolean isAlive() {
        return health > 0;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        if (health < 0) {
            throw new IllegalArgumentException("Здоровье существа не может быть %d!".formatted(health));
        }

        this.health = health;
    }

    public void takeDamage(int damage) {
        int newHealth = Math.max(0, getHealth() - damage);
        setHealth(newHealth);
    }

    public int getSpeed() {
        return speed;
    }

    public PathFinder getPathFinder() {
        return pathFinder;
    }

    protected abstract boolean interact(WorldMap map, Coordinates targetCoordinates, Entity target);
}
