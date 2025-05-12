<?php

namespace App\Entities;

use App\Map;

final class Herbivore extends Creature
{
    public int $health = 10;
    public int $speed = 1;

    public function makeMove(Map $map): void
    {
        if (empty($this->pathToTarget)) {
            $this->setPathToTarget($map);

            if (!$this->pathToTarget) {
                return;
            }

            $this->setTarget();
        }

        $newPosition = $this->getNewPosition();

        if ($map->isEmptyCell($newPosition)
            || $newPosition == $this->target) {
            $this->changePosition($map, $newPosition);
        } else {
            $this->setPathToTarget($map);
            $this->setTarget();

            $newPosition = $this->getNewPosition();

            $this->changePosition($map, $newPosition);
        }

    }

    public function getSprite(): string
    {
        return "\u{1F404}";
    }
}
