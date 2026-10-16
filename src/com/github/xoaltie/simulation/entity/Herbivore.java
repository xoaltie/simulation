package com.github.xoaltie.simulation.entity;

import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.path.BfsPathFinder;

import java.util.List;
import java.util.Objects;

public class Herbivore extends Creature {
    public Herbivore() {
        super(10, 1, new BfsPathFinder());
    }

    @Override
    public void makeMove(WorldMap map) {
        Objects.requireNonNull(map);
        Coordinates current = map.getCoordinates(this);
        List<Coordinates> path = getPathFinder().find(map, current, Grass.class);
        if (path.isEmpty()) {
            return;
        }
        System.out.printf("Creature: %s, current position: %s, path to target: %s%n", this.getClass().getSimpleName(), current, path);
        // TODO: добавить логику поедания травы и перемещения
    }
}
