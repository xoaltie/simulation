package com.github.xoaltie.simulation;

import com.github.xoaltie.simulation.entity.Entity;
import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

public final class EntitySpawner {
    public void spawn(WorldMap map, int countToSpawn, Supplier<? extends Entity> supplier) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(supplier);
        if (countToSpawn < 0) {
            throw new IllegalArgumentException("Нельзя заспавнить %d количество существ!".formatted(countToSpawn));
        }

        List<Coordinates> emptyCell = map.getEmptyCells();

        if (emptyCell.isEmpty() || emptyCell.size() < countToSpawn) {
            throw new IllegalStateException("Карта переполнена. Не удалось заспавнить существ!");
        }

        Collections.shuffle(emptyCell);

        for (int count = 0; count < countToSpawn; count++) {
            Coordinates coordinates = emptyCell.get(count);
            map.addEntity(coordinates, supplier.get());
        }
    }
}
