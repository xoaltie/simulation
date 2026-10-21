package com.github.xoaltie.simulation.entity;

import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.path.BfsPathFinder;

public class Predator extends Creature {
    private final int attack;

    public Predator(int health, int speed, int attack) {
        super(health, speed, new BfsPathFinder());
        if (attack <= 0) {
            throw new IllegalArgumentException("Атака существа не может быть %d!".formatted(attack));
        }

        this.attack = attack;
    }

    @Override
    public void makeMove(WorldMap map) {

    }
}
