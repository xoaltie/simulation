package com.github.xoaltie.simulation;

import java.util.HashMap;
import java.util.Map;

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
        return coordinates.x() >= 0 && coordinates.y() >= 0 &&
                coordinates.x() < this.width && coordinates.y() < this.height;
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        this.ensureWithinBounds(coordinates);
        return !this.entities.containsKey(coordinates);
    }

    public void addEntity(Coordinates coordinates, Entity entity) {
        if (!this.isCellEmpty(coordinates)) {
            throw new IllegalStateException("Некорректные координаты. Позиция занята другой сущностью.");
        }
        this.entities.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates) {
        this.ensureWithinBounds(coordinates);
        this.entities.remove(coordinates);
    }

    public Entity getEntity(Coordinates coordinates) {
        this.ensureWithinBounds(coordinates);
        return this.entities.get(coordinates);
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
