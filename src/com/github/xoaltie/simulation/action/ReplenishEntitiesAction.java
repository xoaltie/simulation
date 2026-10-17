package com.github.xoaltie.simulation.action;

import com.github.xoaltie.simulation.map.WorldMap;

import java.util.Objects;

public class ReplenishEntitiesAction implements Action {
    @Override
    public void execute(WorldMap map) {
        Objects.requireNonNull(map);
    }
}
