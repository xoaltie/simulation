package com.github.xoaltie.simulation;

public class Main {
    static void main() {
        WorldMap map = new WorldMap(10, 10);
        Renderer renderer = new ConsoleRenderer();
        Simulation simulation = new Simulation(map, renderer);
        simulation.start();
    }
}
