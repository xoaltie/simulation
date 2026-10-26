package com.github.xoaltie.simulation.render;

import com.github.xoaltie.simulation.entity.Entity;
import com.github.xoaltie.simulation.map.Coordinates;
import com.github.xoaltie.simulation.map.WorldMap;

import java.util.Objects;
import java.util.Optional;

public final class ConsoleMapRenderer implements MapRenderer {
    private final SpriteMapper spriteMapper;

    public ConsoleMapRenderer(SpriteMapper spriteMapper) {
        this.spriteMapper = spriteMapper;
    }

    @Override
    public void render(WorldMap map, int turnCounter) {
        Objects.requireNonNull(map);

        StringBuilder sb = new StringBuilder();
        sb.append(System.lineSeparator());
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                Optional<Entity> entity = map.getEntity(coordinates);
                String sprite = entity.map(spriteMapper::getSprite)
                        .orElse(spriteMapper.getEmptyCellSprite());
                sb.append(" ").append(sprite).append(" ");
            }
            sb.append(System.lineSeparator());
        }
        sb.append("Текущий ход: ").append(turnCounter).append(System.lineSeparator());
        sb.append("Для остановки игры на паузу нажмите Enter").append(System.lineSeparator());
        System.out.print(sb);
    }
}
