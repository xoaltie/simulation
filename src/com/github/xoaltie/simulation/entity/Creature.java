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
        Objects.requireNonNull(pathFinder, "path finder must not be null");
        Objects.requireNonNull(targetType, "target type must not be null");
        if (health <= 0) {
            throw new IllegalArgumentException("health must be positive, but was: %d".formatted(health));
        }
        if (speed <= 0) {
            throw new IllegalArgumentException("speed must be positive, but was: %d".formatted(speed));
        }

        this.health = health;
        this.speed = speed;
        this.pathFinder = pathFinder;
        this.targetType = targetType;
    }

    public final void makeMove(WorldMap map) {
        Objects.requireNonNull(map, "map must not be null");

        Coordinates current = map.getCoordinates(this);
        List<Coordinates> path = pathFinder.find(map, current, targetType);
        if (path.isEmpty()) {
            return;
        }

        for (int i = 0; i < speed; i++) {
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

    protected abstract boolean interact(WorldMap map, Coordinates targetCoordinates, Entity target);

    public boolean isAlive() {
        return health > 0;
    }

    public void takeDamage(int damage) {
        health = Math.max(0, health - damage);
    }
}
