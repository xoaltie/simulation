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
            throw new IllegalArgumentException("map size must be greater than zero, but was: %d %d".formatted(width, height));
        }

        this.width = width;
        this.height = height;
    }

    public boolean isWithinBounds(Coordinates coordinates) {
        Objects.requireNonNull(coordinates, "coordinates must not be null");
        return coordinates.x() >= 0 && coordinates.y() >= 0 &&
                coordinates.x() < width && coordinates.y() < height;
    }

    private void ensureWithinBounds(Coordinates coordinates) {
        if (!isWithinBounds(coordinates)) {
            throw new IllegalArgumentException("coordinates %s are out of bounds. map size: %dx%d".formatted(coordinates, width, height));
        }
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        ensureWithinBounds(coordinates);
        return !entities.containsKey(coordinates);
    }

    public Optional<Entity> getEntity(Coordinates coordinates) {
        ensureWithinBounds(coordinates);
        return Optional.ofNullable(entities.get(coordinates));
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

    public Coordinates getCoordinates(Entity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        return entities.entrySet().stream()
                .filter(entry -> Objects.equals(entry.getValue(), entity))
                .map(Map.Entry::getKey)
                .toList().getFirst();
    }

    public void addEntity(Coordinates coordinates, Entity entity) {
        Objects.requireNonNull(entity, "entity must not be null");
        ensureWithinBounds(coordinates);

        Entity previousEntity = entities.putIfAbsent(coordinates, entity);
        if (previousEntity != null) {
            throw new IllegalStateException("cannot place entity: cell %s is already occupied".formatted(coordinates));
        }
    }

    public void removeEntity(Coordinates coordinates) {
        ensureWithinBounds(coordinates);
        Entity removedEntity = entities.remove(coordinates);
        if (removedEntity == null) {
            throw new IllegalStateException("cannot remove entity: cell %s is empty".formatted(coordinates));
        }
    }

    public void moveEntity(Coordinates from, Coordinates to) {
        ensureWithinBounds(from);
        ensureWithinBounds(to);

        Entity entity = entities.get(from);
        if (entity == null) {
            throw new IllegalStateException("cannot move entity: cell %s is empty".formatted(from));
        }

        Entity previousEntity = entities.putIfAbsent(to, entity);
        if (previousEntity != null) {
            throw new IllegalStateException("cannot move entity: cell %s is already occupied".formatted(to));
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
}
