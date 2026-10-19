package com.github.xoaltie.simulation.action;

import com.github.xoaltie.simulation.EntitySpawner;
import com.github.xoaltie.simulation.config.SimulationConfig;
import com.github.xoaltie.simulation.entity.Grass;
import com.github.xoaltie.simulation.entity.Herbivore;
import com.github.xoaltie.simulation.entity.Predator;
import com.github.xoaltie.simulation.entity.Rock;
import com.github.xoaltie.simulation.entity.Tree;
import com.github.xoaltie.simulation.map.WorldMap;

import java.util.Objects;

public class MapPopulationAction implements Action {
    private final EntitySpawner spawner;

    public MapPopulationAction(EntitySpawner spawner) {
        this.spawner = spawner;
    }

    @Override
    public void execute(WorldMap map) {
        Objects.requireNonNull(map);

        spawner.spawn(map, SimulationConfig.ROCK_START_COUNT, Rock::new);
        spawner.spawn(map, SimulationConfig.TREE_START_COUNT, Tree::new);
        spawner.spawn(map, SimulationConfig.GRASS_START_COUNT, Grass::new);
        spawner.spawn(map, SimulationConfig.HERBIVORE_START_COUNT, Herbivore::new);
        spawner.spawn(map, SimulationConfig.PREDATOR_START_COUNT, Predator::new);
    }
}