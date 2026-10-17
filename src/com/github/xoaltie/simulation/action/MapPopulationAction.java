package com.github.xoaltie.simulation.action;

import com.github.xoaltie.simulation.config.SimulationConfig;
import com.github.xoaltie.simulation.entity.Entity;
import com.github.xoaltie.simulation.entity.Grass;
import com.github.xoaltie.simulation.entity.Herbivore;
import com.github.xoaltie.simulation.entity.Predator;
import com.github.xoaltie.simulation.entity.Rock;
import com.github.xoaltie.simulation.entity.Tree;
import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;

import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;

public class MapPopulationAction implements Action {
    @Override
    public void execute(WorldMap map) {
        Objects.requireNonNull(map);

        spawnEntity(map, SimulationConfig.ROCK_START_COUNT, Rock::new);
        spawnEntity(map, SimulationConfig.TREE_START_COUNT, Tree::new);
        spawnEntity(map, SimulationConfig.GRASS_START_COUNT, Grass::new);
        spawnEntity(map, SimulationConfig.HERBIVORE_START_COUNT, Herbivore::new);
        spawnEntity(map, SimulationConfig.PREDATOR_START_COUNT, Predator::new);
    }

    // TODO: захерачить этот метод в отдельный класс EntitySpawner, который передам в конструкторы порождающих экшенов
    //  переложить ответственность за поиск пустой клетки на WorldMap
    //  карта должна возвращать state эксепшен, если пустой клетки нет
    private void spawnEntity(WorldMap map, int countToSpawn, Supplier<? extends Entity> supplier) {
        int count = 0;
        int fail = 0;
        while (count < countToSpawn && fail < SimulationConfig.MAX_SPAWN_FAIL) {
            Coordinates coordinates = new Coordinates(
                    ThreadLocalRandom.current().nextInt(map.getWidth()),
                    ThreadLocalRandom.current().nextInt(map.getHeight()));
            if (map.isCellEmpty(coordinates)) {
                map.addEntity(coordinates, supplier.get());
                count++;
                fail = 0;
            } else {
                fail++;
            }
        }
    }
}