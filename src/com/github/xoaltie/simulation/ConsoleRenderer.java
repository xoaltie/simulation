package com.github.xoaltie.simulation;

import java.util.Objects;
import java.util.Optional;

// TODO: подумать о расхождении получения спрайтов: вроде константы для пустых ячеек и getSprite() для сущностей
//  написать отдельный класс, где будут маппиться сущности и их спрайты, а его уже воткнуть как поле в рендерер
//  и получать из него спрайты через переписанный getSprite()
public class ConsoleRenderer implements Renderer {
    private static final String EMPTY_CELL_SPRITE = "_";

    @Override
    public void render(WorldMap map) {
        Objects.requireNonNull(map);

        StringBuilder sb = new StringBuilder();
        for (int y = 0; y < map.getHeight(); y++) {
            for (int x = 0; x < map.getWidth(); x++) {
                Coordinates coordinates = new Coordinates(x, y);
                Optional<Entity> entity = map.getEntity(coordinates);
                String sprite = entity.map(this::getSprite)
                        .orElse(EMPTY_CELL_SPRITE);
                sb.append(" ").append(sprite).append(" ");
            }
            sb.append(System.lineSeparator());
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
