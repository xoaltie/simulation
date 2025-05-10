<?php

namespace App\Entities;

use App\AStar;
use App\Coordinates;
use App\Map;

final class Herbivore extends Creature
{
    public int $health = 10;
    public int $speed = 1;
    public ?array $pathToTarget = null;

    public function makeMove(Map $map): void
    {
        if ($this->pathToTarget === null) {
            $this->pathToTarget = AStar::findPath($map, $this->position, Grass::class);
        }

        /** @var Coordinates $newPosition */
        $newPosition = array_shift($this->pathToTarget);

        if ($map->isEmptyCell($newPosition)) {
            unset($map->entities[$this->position->convertToKey()]);

            $map->entities[$newPosition->convertToKey()] = $this;

            $this->position = $newPosition;
        } else {
            $this->pathToTarget = AStar::findPath($map, $this->position, Grass::class);

            unset($map->entities[$this->position->convertToKey()]);

            $map->entities[$newPosition->convertToKey()] = $this;

            $this->position = $newPosition;
        }

    }

    public function getSprite(): string
    {
        return "\u{1F404}";
    }
}
