package com.github.xoaltie.simulation.map;

import com.github.xoaltie.simulation.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class WorldMap {
    private final int width;
    private final int height;

    private final Map<Coordinates, Entity> entities = new HashMap<>();

    public WorldMap(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Некорректный размер карты!");
        }
        this.width = width;
        this.height = height;
    }

    public boolean isWithinBounds(Coordinates coordinates) {
        Objects.requireNonNull(coordinates);
        return coordinates.x() >= 0 && coordinates.y() >= 0 &&
                coordinates.x() < width && coordinates.y() < height;
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        ensureWithinBounds(coordinates);
        return !entities.containsKey(coordinates);
    }

    public void addEntity(Coordinates coordinates, Entity entity) {
        Objects.requireNonNull(entity);
        ensureWithinBounds(coordinates);
        Entity previousEntity = entities.putIfAbsent(coordinates, entity);
        if (previousEntity != null) {
            throw new IllegalStateException("Некорректные координаты. Позиция занята другой сущностью.");
        }
    }

    public void removeEntity(Coordinates coordinates) {
        ensureWithinBounds(coordinates);
        Entity removedEntity = entities.remove(coordinates);
        if (removedEntity == null) {
            throw new IllegalStateException("Некорректные координаты. Позиция не содержит сущности.");
        }
    }

    public Optional<Entity> getEntity(Coordinates coordinates) {
        ensureWithinBounds(coordinates);
        return Optional.ofNullable(entities.get(coordinates));
    }

    public Coordinates getCoordinates(Entity entity) {
        Objects.requireNonNull(entity);
        return entities.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), entity))
                .map(Map.Entry::getKey)
                .toList().getFirst();
    }

    public Collection<Entity> getEntities() {
        return List.copyOf(entities.values());
    }

    public List<Coordinates> getEmptyCells() {
        List<Coordinates> emptyCell = new ArrayList<>();
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                Coordinates coordinates = new Coordinates(x, y);
                if (!entities.containsKey(coordinates)) {
                    emptyCell.add(coordinates);
                }
            }
        }

        return emptyCell;
    }

    public void moveEntity(Coordinates from, Coordinates to) {
        Objects.requireNonNull(from);
        Objects.requireNonNull(to);
        ensureWithinBounds(from);
        ensureWithinBounds(to);

        Entity entity = entities.get(from);
        if (entity == null) {
            throw new IllegalStateException("Некорректные координаты. Позиция не содержит сущности.");
        }
        Entity previousEntity = entities.putIfAbsent(to, entity);
        if (previousEntity != null) {
            throw new IllegalStateException("Некорректные координаты. Позиция занята другой сущностью.");
        }

        entities.remove(from);
    }

    public void clear() {
        entities.clear();
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    private void ensureWithinBounds(Coordinates coordinates) {
        if (!isWithinBounds(coordinates)) {
            throw new IllegalArgumentException("Некорректные координаты. Выход за пределы карты.");
        }
    }
}
