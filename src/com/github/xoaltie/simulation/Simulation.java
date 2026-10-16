package com.github.xoaltie.simulation;

import com.github.xoaltie.simulation.entity.Creature;
import com.github.xoaltie.simulation.entity.Entity;
import com.github.xoaltie.simulation.entity.Grass;
import com.github.xoaltie.simulation.entity.Herbivore;
import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.render.Renderer;

import java.util.Collection;
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
        Collection<Entity> entities = map.getEntities();
        for (Entity entity : entities) {
            if (entity instanceof Creature) {
                ((Creature) entity).makeMove(map);
            }
        }
    }

    private void init() {
        map.addEntity(new Coordinates(3, 4), new Herbivore());
        map.addEntity(new Coordinates(0, 2), new Herbivore());
        map.addEntity(new Coordinates(2, 5), new Herbivore());
        map.addEntity(new Coordinates(4, 3), new Grass());
    }
}
