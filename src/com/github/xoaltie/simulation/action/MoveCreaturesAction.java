package com.github.xoaltie.simulation.action;

import com.github.xoaltie.simulation.entity.Creature;
import com.github.xoaltie.simulation.entity.Entity;
import com.github.xoaltie.simulation.map.WorldMap;

import java.util.Collection;
import java.util.Objects;

public class MoveCreaturesAction implements Action {
    @Override
    public void execute(WorldMap map) {
        Objects.requireNonNull(map);

        Collection<Entity> entities = map.getEntities();

        for (Entity entity : entities) {
            if (entity instanceof Creature creature && creature.isAlive()) {
                creature.makeMove(map);
            }
        }
    }
}
