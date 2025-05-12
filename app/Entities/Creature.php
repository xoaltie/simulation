<?php

namespace App\Entities;

use App\AStar;
use App\Coordinates;
use App\Map;

abstract class Creature extends Entity
{
    public int $health;
    public int $speed;

    /** @var array<int, Coordinates>|false|null $pathToTarget */
    public array|false|null $pathToTarget = null;
    public ?Coordinates $target = null;

    abstract public function makeMove(Map $map): void;

    protected function changePosition(Map $map, Coordinates $newPosition): void
    {
        unset($map->entities[$this->position->convertToKey()]);

        $map->entities[$newPosition->convertToKey()] = $this;

        $this->position = $newPosition;

    }

    protected function setTarget(): void
    {
        $this->target = $this->pathToTarget[count($this->pathToTarget) - 1];
    }

    protected function getNewPosition(): Coordinates
    {
        return array_shift($this->pathToTarget);
    }

    protected function setPathToTarget(Map $map): void
    {
        $this->pathToTarget = AStar::findPath($map, $this->position, Grass::class);
    }
}
