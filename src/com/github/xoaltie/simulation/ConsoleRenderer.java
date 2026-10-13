package com.github.xoaltie.simulation;

import java.util.Optional;

public class ConsoleRenderer implements Renderer {
    private static final String EMPTY_CELL_SPRITE = "_";

    @Override
    public void render(WorldMap map) {
        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                Optional<Entity> entity = map.getEntity(coordinates);
                String sprite = entity.map(this::getSprite)
                        .orElse(EMPTY_CELL_SPRITE);
                sb.append(" ").append(sprite).append(" ");
            }
            sb.append("\n");
        }
        System.out.print(sb);
    }

    private String getSprite(Entity entity) {
        return switch (entity) {
            case Grass _ -> "G";
            case Herbivore _ -> "H";
            default -> "";
        };

    }
}
