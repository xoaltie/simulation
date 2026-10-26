package com.github.xoaltie.simulation;

import com.github.xoaltie.simulation.action.Action;
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
    static void main() {
        Scanner scanner = new Scanner(System.in);
        Simulation simulation = createSimulation();
        MenuRenderer menuRenderer = new ConsoleMenuRenderer();
        while (true) {
            menuRenderer.printStartMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    runSimulationLoop(simulation, scanner, menuRenderer);
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
        Simulation simulation = new Simulation(map, mapRenderer, initActions, turnActions);
        return simulation;
    }

    private static void runSimulationLoop(Simulation simulation, Scanner scanner, MenuRenderer renderer) {
        simulation.start();
        while (true) {
            scanner.nextLine();
            simulation.pause();
            if (!handlePauseMenu(simulation, scanner, renderer)) {
                return;
            }
        }
    }

    private static boolean handlePauseMenu(Simulation simulation, Scanner scanner, MenuRenderer render) {
        while (true) {
            render.printPauseMenu();
            String input = scanner.nextLine().trim();
            switch (input) {
                case "1":
                    simulation.resume();
                    return true;
                case "2":
                    simulation.stop();
                    return false;
                default:
                    render.printInvalidInput();
            }
        }
    }
}
