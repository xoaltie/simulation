package com.github.xoaltie.simulation.path;

import com.github.xoaltie.simulation.entity.Entity;
import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;

import java.util.List;

public interface PathFinder {
    List<Coordinates> find(WorldMap map, Coordinates start, Class<? extends Entity> target);
}
