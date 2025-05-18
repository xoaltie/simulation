<?php

namespace App\Entities;

use App\AStar;
use App\Coordinates;
use App\Map;

abstract class Creature extends Entity
{
    public int $health;

    /** @var array<int, Coordinates>|false|null $pathToTarget */
    public array|false|null $pathToTarget = null;
    public ?Coordinates $targetCoordinates = null;

    /** @var class-string $targetEntityClass */
    public string $targetEntityClass;

    abstract public function makeMove(Map $map): void;

    protected function changePosition(Map $map, Coordinates $newPosition): void
    {
        $map->removeEntity($this->position);

        $map->spawn($newPosition, $this);

    }

    protected function setTargetCoordinates(): void
    {
        $this->targetCoordinates = $this->pathToTarget[count($this->pathToTarget) - 1];
    }

    protected function getNewPosition(): Coordinates
    {
        return array_shift($this->pathToTarget);
    }

    protected function setPathToTarget(Map $map): void
    {
        $this->pathToTarget = AStar::findPath($map, $this->position, $this->targetEntityClass);
    }

    protected function isTargetValid(Map $map): bool
    {
        $entity = $map->getEntity($this->targetCoordinates);

        if (!$entity) {
            return false;
        }

        if ($entity::class === $this->targetEntityClass) {
            return true;
        }

        return false;
    }

    protected function isHealthPositive(): bool
    {
        return $this->health > 0;
    }
}
