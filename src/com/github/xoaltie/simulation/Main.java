package com.github.xoaltie.simulation;

import com.github.xoaltie.simulation.action.Action;
import com.github.xoaltie.simulation.action.EntitySpawner;
import com.github.xoaltie.simulation.action.MapPopulationAction;
import com.github.xoaltie.simulation.action.MoveCreaturesAction;
import com.github.xoaltie.simulation.action.ReplenishEntitiesAction;
import com.github.xoaltie.simulation.config.SimulationConfig;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.render.ConsoleMapRenderer;
import com.github.xoaltie.simulation.render.ConsoleMenuRenderer;
import com.github.xoaltie.simulation.render.MapRenderer;
import com.github.xoaltie.simulation.render.MenuRenderer;
import com.github.xoaltie.simulation.render.SpriteMapper;

import java.util.List;
import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final MenuRenderer menuRenderer = new ConsoleMenuRenderer();

    static void main() {
        Simulation simulation = createSimulation();

        while (true) {
            menuRenderer.printStartMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    runSimulationLoop(simulation);
                    break;
                case "2":
                    return;
                default:
                    menuRenderer.printInvalidInput();
            }
        }
    }

    private static Simulation createSimulation() {
        WorldMap map = new WorldMap(SimulationConfig.MAP_WIDTH, SimulationConfig.MAP_HEIGHT);
        EntitySpawner spawner = new EntitySpawner();
        SpriteMapper spriteMapper = new SpriteMapper();
        MapRenderer mapRenderer = new ConsoleMapRenderer(spriteMapper);
        List<Action> initActions = List.of(new MapPopulationAction(spawner));
        List<Action> turnActions = List.of(new MoveCreaturesAction(), new ReplenishEntitiesAction(spawner));

        return new Simulation(map, mapRenderer, initActions, turnActions);
    }

    private static void runSimulationLoop(Simulation simulation) {
        simulation.start();
        while (true) {
            scanner.nextLine();
            simulation.pause();
            if (!handlePauseMenu(simulation)) {
                return;
            }
        }
    }

    private static boolean handlePauseMenu(Simulation simulation) {
        while (true) {
            menuRenderer.printPauseMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    simulation.resume();
                    return true;
                case "2":
                    simulation.stop();
                    return false;
                default:
                    menuRenderer.printInvalidInput();
            }
        }
    }
}
