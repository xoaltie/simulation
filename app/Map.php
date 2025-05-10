<?php

namespace App;

use App\Entities\Entity;

final class Map
{
    public const int WIDTH = 10;
    public const int HEIGHT = 10;

    /** @var array<string, Entity> $entities */
    public array $entities;

    public function spawn(Coordinates $coordinates, Entity $entity): void
    {
        $entity->position = $coordinates;

        $this->entities[$coordinates->convertToKey()] = $entity;

        ksort($this->entities);
    }

    public function getEntity(Coordinates $coordinates): Entity
    {
        return $this->entities[$coordinates->convertToKey()];
    }

    public function isEmptyCell(Coordinates $coordinates): bool
    {
        return !array_key_exists($coordinates->convertToKey(), $this->entities);
    }
}
