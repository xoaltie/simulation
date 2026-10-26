package com.github.xoaltie.simulation.action;

import com.github.xoaltie.simulation.config.SimulationConfig;
import com.github.xoaltie.simulation.entity.Entity;
import com.github.xoaltie.simulation.entity.Grass;
import com.github.xoaltie.simulation.entity.Herbivore;
import com.github.xoaltie.simulation.entity.Predator;
import com.github.xoaltie.simulation.map.WorldMap;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

public class ReplenishEntitiesAction implements Action {
    private final EntitySpawner spawner;

    public ReplenishEntitiesAction(EntitySpawner spawner) {
        this.spawner = spawner;
    }

    @Override
    public void execute(WorldMap map) {
        Objects.requireNonNull(map, "map must not be null");

        Collection<Entity> entities = map.getEntities();
        Map<Class<? extends Entity>, Integer> entitiesCount = entities.stream()
                .collect(Collectors.groupingBy(
                        Entity::getClass,
                        Collectors.collectingAndThen(
                                Collectors.counting(),
                                Long::intValue
                        )
                ));

        spawner.spawn(map, Math.max(0, SimulationConfig.GRASS_MAX_COUNT - entitiesCount.getOrDefault(Grass.class, 0)), Grass::new);
        spawner.spawn(map, Math.max(0, SimulationConfig.HERBIVORE_MAX_COUNT - entitiesCount.getOrDefault(Herbivore.class, 0)),
                () -> new Herbivore(SimulationConfig.HERBIVORE_HEALTH, SimulationConfig.HERBIVORE_SPEED));
        spawner.spawn(map, Math.max(0, SimulationConfig.PREDATOR_MAX_COUNT - entitiesCount.getOrDefault(Predator.class, 0)),
                () -> new Predator(SimulationConfig.PREDATOR_HEALTH, SimulationConfig.PREDATOR_SPEED, SimulationConfig.PREDATOR_ATTACK));
    }
}
