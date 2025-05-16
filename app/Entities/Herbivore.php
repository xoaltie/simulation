<?php

namespace App\Entities;

use App\Map;

final class Herbivore extends Creature
{
    public int $health = 10;
    public string $targetEntityClass = Grass::class;

    public function makeMove(Map $map): void
    {
        if (!$this->isHealthPositive()) {
            unset($map->entities[$this->position->convertToKey()]);

            return;
        }

        if (empty($this->pathToTarget)) {
            $this->setPathToTarget($map);

            if (!$this->pathToTarget) {
                return;
            }

            $this->setTargetCoordinates();
        }

        if (!$this->isTargetValid($map)) {
            $this->setPathToTarget($map);
            $this->setTargetCoordinates();
        }

        $newPosition = $this->getNewPosition();

        if (!$map->isEmptyCell($newPosition)
            && $newPosition != $this->targetCoordinates) {
            $this->setPathToTarget($map);

            if (!$this->pathToTarget) {
                return;
            }

            $this->setTargetCoordinates();

            $newPosition = $this->getNewPosition();
        }

        $this->changePosition($map, $newPosition);
    }

    public function getSprite(): string
    {
        return "\u{1F404}";
    }
}
