package com.github.xoaltie.simulation.config;

public final class SimulationConfig {
    public static int MAP_WIDTH = 30;
    public static int MAP_HEIGHT = 10;

    public static int HERBIVORE_HEALTH = 10;
    public static int HERBIVORE_SPEED = 1;
    public static int PREDATOR_HEALTH = 10;
    public static int PREDATOR_SPEED = 2;
    public static int PREDATOR_ATTACK = 5;

    public static int ROCK_START_COUNT = 10;
    public static int TREE_START_COUNT = 10;
    public static int GRASS_START_COUNT = 5;
    public static int HERBIVORE_START_COUNT = 7;
    public static int PREDATOR_START_COUNT = 3;

    public static int GRASS_MAX_COUNT = 7;
    public static int HERBIVORE_MAX_COUNT = 10;
    public static int PREDATOR_MAX_COUNT = 5;

    private SimulationConfig() {
    }
}
