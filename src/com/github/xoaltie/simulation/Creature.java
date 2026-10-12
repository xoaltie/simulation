package com.github.xoaltie.simulation;

public abstract class Creature extends Entity {
    private final int speed;
    private int health;

    public Creature(int health, int speed) {
        this.health = health;
        this.speed = speed;
    }

    public abstract void makeMove();

    public int getHealth() {
        return health;
    }

    public int getSpeed() {
        return speed;
    }
}
