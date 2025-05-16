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

    public function getEntity(Coordinates $coordinates): Entity|bool
    {
        if (array_key_exists($coordinates->convertToKey(), $this->entities)) {
            return $this->entities[$coordinates->convertToKey()];
        }

        return false;
    }

    public function isEmptyCell(Coordinates $coordinates): bool
    {
        return !array_key_exists($coordinates->convertToKey(), $this->entities);
    }

    public function getSpawnPosition(): Coordinates|false
    {
        do {
            $coordinates = new Coordinates(random_int(0, Map::HEIGHT - 1), random_int(0, Map::WIDTH - 1));

            if ($this->isEmptyCell($coordinates)) {
                return $coordinates;
            }

        } while (!$this->isEmptyCell($coordinates));

        return false;
    }
}
