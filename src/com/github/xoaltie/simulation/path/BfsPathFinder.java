package com.github.xoaltie.simulation.path;

import com.github.xoaltie.simulation.entity.Entity;
import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public final class BfsPathFinder implements PathFinder {
    private static final int[][] DIRECTIONS = {{0, -1}, {1, 0}, {0, 1}, {-1, 0}};

    public List<Coordinates> find(WorldMap map, Coordinates start, Class<? extends Entity> target) {
        Objects.requireNonNull(map, "map must not be null");
        Objects.requireNonNull(start, "start coordinates must not be null");
        Objects.requireNonNull(target, "target must not be null");
        if (!map.isWithinBounds(start)) {
            throw new IllegalArgumentException("coordinates %s are out of bounds. map size: %dx%d".formatted(start, map.getWidth(), map.getHeight()));
        }

        ArrayDeque<Coordinates> queue = new ArrayDeque<>();
        Set<Coordinates> visited = new HashSet<>();
        Map<Coordinates, Coordinates> parent = new HashMap<>();

        queue.add(start);
        visited.add(start);
        while (!queue.isEmpty()) {
            Coordinates current = queue.poll();
            List<Coordinates> neighbors = getNeighbors(map, current);
            for (Coordinates neighbor : neighbors) {
                if (visited.contains(neighbor)) {
                    continue;
                }

                Optional<Entity> neighborEntity = map.getEntity(neighbor);
                if (neighborEntity.isPresent() && target.isInstance(neighborEntity.get())) {
                    parent.put(neighbor, current);
                    return getPath(parent, neighbor);
                }

                if (neighborEntity.isPresent()) {
                    visited.add(neighbor);
                    continue;
                }

                parent.put(neighbor, current);
                queue.add(neighbor);
                visited.add(neighbor);
            }
        }

        return List.of();
    }

    private List<Coordinates> getNeighbors(WorldMap map, Coordinates start) {
        List<Coordinates> neighbors = new ArrayList<>();

        for (int[] direction : DIRECTIONS) {
            Coordinates neighbor = new Coordinates(start.x() + direction[0], start.y() + direction[1]);
            if (map.isWithinBounds(neighbor)) {
                neighbors.add(neighbor);
            }
        }

        return neighbors;
    }

    private List<Coordinates> getPath(Map<Coordinates, Coordinates> parents, Coordinates targetCoordinates) {
        List<Coordinates> path = new ArrayList<>();
        Coordinates current = targetCoordinates;

        while (current != null) {
            path.add(current);
            current = parents.get(current);
        }

        path.removeLast();
        return path.reversed();
    }
}
