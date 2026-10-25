package com.github.xoaltie.simulation;

import com.github.xoaltie.simulation.action.Action;
import com.github.xoaltie.simulation.action.MapPopulationAction;
import com.github.xoaltie.simulation.action.MoveCreaturesAction;
import com.github.xoaltie.simulation.action.ReplenishEntitiesAction;
import com.github.xoaltie.simulation.config.SimulationConfig;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.render.ConsoleRenderer;
import com.github.xoaltie.simulation.render.Renderer;
import com.github.xoaltie.simulation.render.SpriteMapper;

import java.util.List;

public class Main {
    static void main() {
        WorldMap map = new WorldMap(SimulationConfig.MAP_WIDTH, SimulationConfig.MAP_HEIGHT);
        EntitySpawner spawner = new EntitySpawner();
        SpriteMapper spriteMapper = new SpriteMapper();
        Renderer renderer = new ConsoleRenderer(spriteMapper);
        List<Action> initActions = List.of(new MapPopulationAction(spawner));
        List<Action> turnActions = List.of(new MoveCreaturesAction(), new ReplenishEntitiesAction(spawner));
        Simulation simulation = new Simulation(map, renderer, initActions, turnActions);
        simulation.start();
    }
}
