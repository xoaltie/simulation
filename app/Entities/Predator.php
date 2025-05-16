<?php

namespace App\Entities;

use App\Map;

final class Predator extends Creature
{
    public int $health = 20;
    public int $bite = 6;
    public string $targetEntityClass = Herbivore::class;

    public function makeMove(Map $map): void
    {
        $this->setPathToTarget($map);

        if (!$this->pathToTarget) {
            return;
        }

        $this->setTargetCoordinates();

        $newPosition = $this->getNewPosition();

        if ($newPosition == $this->targetCoordinates) {
            $target = $map->getEntity($this->targetCoordinates);

            $target->health -= $this->bite;

            return;
        }

        $this->changePosition($map, $newPosition);
    }

    public function getSprite(): string
    {
        return "\u{1F415}";
    }
}
