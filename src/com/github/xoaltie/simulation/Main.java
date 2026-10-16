package com.github.xoaltie.simulation;

import com.github.xoaltie.simulation.map.WorldMap;
import com.github.xoaltie.simulation.render.ConsoleRenderer;
import com.github.xoaltie.simulation.render.Renderer;

public class Main {
    static void main() {
        WorldMap map = new WorldMap(10, 10);
        Renderer renderer = new ConsoleRenderer();
        Simulation simulation = new Simulation(map, renderer);
        simulation.start();
    }
}
