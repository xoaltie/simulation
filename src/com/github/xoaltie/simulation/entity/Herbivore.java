package com.github.xoaltie.simulation.entity;

import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.path.BfsPathFinder;

public final class Herbivore extends Creature {
    public Herbivore(int health, int speed) {
        super(health, speed, new BfsPathFinder(), Grass.class);
    }

    @Override
    protected boolean interact(WorldMap map, Coordinates targetCoordinates, Entity target) {
        map.removeEntity(targetCoordinates);
        return true;
    }
}
