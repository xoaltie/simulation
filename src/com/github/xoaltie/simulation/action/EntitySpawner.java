package com.github.xoaltie.simulation.action;

import com.github.xoaltie.simulation.entity.Entity;
import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class EntitySpawner {
    public void spawn(WorldMap map, int countToSpawn, Supplier<? extends Entity> supplier) {
        Objects.requireNonNull(map, "map must not be null");
        Objects.requireNonNull(supplier, "supplier must not be null");
        if (countToSpawn < 0) {
            throw new IllegalArgumentException("count to spawn must be non-negative, but was: %d".formatted(countToSpawn));
        }

        if (countToSpawn == 0) {
            return;
        }

        List<Coordinates> emptyCell = map.getEmptyCells();

        if (emptyCell.isEmpty() || emptyCell.size() < countToSpawn) {
            throw new IllegalStateException("not enough empty cells to spawn entities: requested %d, but only %d available".formatted(countToSpawn, emptyCell.size()));
        }

        Collections.shuffle(emptyCell);

        for (int count = 0; count < countToSpawn; count++) {
            Coordinates coordinates = emptyCell.get(count);
            map.addEntity(coordinates, supplier.get());
        }
    }
}
