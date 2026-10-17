package com.github.xoaltie.simulation;

import com.github.xoaltie.simulation.action.Action;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.render.Renderer;

import java.util.List;
import java.util.Objects;
import java.util.Scanner;

public final class Simulation {
    private final WorldMap map;
    private final Renderer renderer;
    private final List<Action> initActions;
    private final List<Action> turnActions;
    private int turnCounter;

    public Simulation(WorldMap map, Renderer renderer, List<Action> initActions, List<Action> turnActions) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(renderer);
        Objects.requireNonNull(initActions);
        Objects.requireNonNull(turnActions);

        this.map = map;
        this.renderer = renderer;
        this.initActions = initActions;
        this.turnActions = turnActions;

        performActions(initActions);
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        while (true) {
            nextTurn();
            scanner.nextLine();
        }
    }

    public void pause() {
        // TODO: реализовать после написания готового игрового цикла
    }

    public void nextTurn() {
        turnCounter++;
        renderer.render(map);
        System.out.printf("Текущий ход: %d\n", turnCounter);
        performActions(turnActions);
    }

    private void performActions(List<Action> actions) {
        for (Action action : actions) {
            action.execute(map);
        }
    }
}
