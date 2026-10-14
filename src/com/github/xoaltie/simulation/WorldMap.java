package com.github.xoaltie.simulation;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

// TODO: добавить проверку на null
//  перевести сообщения исключений на английский и возможно добавить полезных отладочных данных
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
                coordinates.x() < width && coordinates.y() < height;
    }

    public boolean isCellEmpty(Coordinates coordinates) {
        ensureWithinBounds(coordinates);
        return !entities.containsKey(coordinates);
    }

    public void addEntity(Coordinates coordinates, Entity entity) {
        if (!isCellEmpty(coordinates)) {
            throw new IllegalStateException("Некорректные координаты. Позиция занята другой сущностью.");
        }
        entities.put(coordinates, entity);
    }

    public void removeEntity(Coordinates coordinates) {
        if (isCellEmpty(coordinates)) {
            throw new IllegalStateException("Некорректные координаты. Позиция не содержит сущности.");
        }
        entities.remove(coordinates);
    }

    public Optional<Entity> getEntity(Coordinates coordinates) {
        ensureWithinBounds(coordinates);
        return Optional.ofNullable(entities.get(coordinates));
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
