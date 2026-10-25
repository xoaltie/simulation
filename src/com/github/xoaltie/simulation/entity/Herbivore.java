package com.github.xoaltie.simulation.entity;

import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.path.BfsPathFinder;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

public final class Herbivore extends Creature {
    public Herbivore(int health, int speed) {
        super(health, speed, new BfsPathFinder());
    }

    @Override
    public void makeMove(WorldMap map) {
        Objects.requireNonNull(map);

        Coordinates current = map.getCoordinates(this);
        List<Coordinates> path = getPathFinder().find(map, current, Grass.class);
        if (path.isEmpty()) {
            return;
        }

        // TODO: переопределить логику по подобию с хищником
        int nextCellIndex = Math.min(getSpeed(), path.size()) - 1;
        Coordinates nextCell = path.get(nextCellIndex);
        Optional<Entity> entityOptional = map.getEntity(nextCell);
        if (entityOptional.isPresent()) {
            eat(map, nextCell);
        }
        map.moveEntity(current, nextCell);
    }

    private void eat(WorldMap map, Coordinates targetCell) {
        map.removeEntity(targetCell);
    }
}
