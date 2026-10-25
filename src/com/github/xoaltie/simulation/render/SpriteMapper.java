package com.github.xoaltie.simulation.render;

import com.github.xoaltie.simulation.entity.Entity;
import com.github.xoaltie.simulation.entity.Grass;
import com.github.xoaltie.simulation.entity.Herbivore;
import com.github.xoaltie.simulation.entity.Predator;
import com.github.xoaltie.simulation.entity.Rock;
import com.github.xoaltie.simulation.entity.Tree;

import java.util.Objects;

public final class SpriteMapper {
    public String getSprite(Entity entity) {
        Objects.requireNonNull(entity);

        return switch (entity) {
            case Rock _ -> "\uD83E\uDEA8";
            case Tree _ -> "\uD83C\uDF33";
            case Grass _ -> "\uD83C\uDF31";
            case Herbivore _ -> "\uD83D\uDC30";
            case Predator _ -> "\uD83D\uDC3A";
        };
    }

    public String getEmptyCellSprite() {
        return "\uD83D\uDFEB";
    }
}
