package com.github.xoaltie.simulation;

import java.util.Objects;
import java.util.Scanner;

public final class Simulation {
    private final WorldMap map;
    private final Renderer renderer;
    private int turnCounter;


    public Simulation(WorldMap map, Renderer renderer) {
        Objects.requireNonNull(map);
        Objects.requireNonNull(renderer);
        this.map = map;
        this.renderer = renderer;
        init();
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
    }

    private void init() {
        map.addEntity(new Coordinates(3, 4), new Herbivore());
        map.addEntity(new Coordinates(0, 2), new Herbivore());
        map.addEntity(new Coordinates(2, 5), new Herbivore());
        map.addEntity(new Coordinates(4, 3), new Grass());
    }
}
