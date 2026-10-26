package com.github.xoaltie.simulation.entity;

import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.path.BfsPathFinder;

public final class Predator extends Creature {
    private final int attack;

    public Predator(int health, int speed, int attack) {
        super(health, speed, new BfsPathFinder(), Herbivore.class);
        if (attack <= 0) {
            throw new IllegalArgumentException("Атака существа не может быть %d!".formatted(attack));
        }

        this.attack = attack;
    }

    @Override
    protected boolean interact(WorldMap map, Coordinates targetCoordinates, Entity target) {
        if (target instanceof Creature creature) {
            creature.takeDamage(attack);
            if (!creature.isAlive()) {
                map.removeEntity(targetCoordinates);
                return true;
            }
        }

        return false;
    }
}
