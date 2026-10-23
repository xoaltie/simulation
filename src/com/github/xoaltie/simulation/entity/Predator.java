package com.github.xoaltie.simulation.entity;

import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.path.BfsPathFinder;

import java.util.List;
import java.util.Objects;

public class Predator extends Creature {
    private final int attack;

    public Predator(int health, int speed, int attack) {
        super(health, speed, new BfsPathFinder());
        if (attack <= 0) {
            throw new IllegalArgumentException("Атака существа не может быть %d!".formatted(attack));
        }

        this.attack = attack;
    }

    @Override
    public void makeMove(WorldMap map) {
        Objects.requireNonNull(map);

        Coordinates current = map.getCoordinates(this);
        List<Coordinates> path = getPathFinder().find(map, current, Herbivore.class);
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
            boolean isTargetDead = attack(map, nextCell, target);
            if (isTargetDead) {
                map.moveEntity(current, nextCell);
                return;
            }
        }
    }

    private boolean attack(WorldMap map, Coordinates targetCell, Entity target) {
        if (target instanceof Creature creature) {
            creature.takeDamage(attack);
            if (creature.getHealth() == 0) {
                map.removeEntity(targetCell);
                return true;
            }
        }

        return false;
    }
}
