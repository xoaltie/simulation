package com.github.xoaltie.simulation.action;

import com.github.xoaltie.simulation.EntitySpawner;
import com.github.xoaltie.simulation.map.WorldMap;

import java.util.Objects;

public class ReplenishEntitiesAction implements Action {
    private final EntitySpawner spawner;

    public ReplenishEntitiesAction(EntitySpawner spawner) {
        this.spawner = spawner;
    }

    @Override
    public void execute(WorldMap map) {
        Objects.requireNonNull(map);
    }
}
