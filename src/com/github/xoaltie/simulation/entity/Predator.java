package com.github.xoaltie.simulation.entity;

import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.path.BfsPathFinder;

public class Predator extends Creature {
    public Predator() {
        super(10, 1, new BfsPathFinder());
    }

    @Override
    public void makeMove(WorldMap map) {

    }
}
