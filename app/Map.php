<?php

namespace App;

use App\Entities\Entity;

final class Map
{
    public const int WIDTH = 10;
    public const int HEIGHT = 10;

    /** @var array<string, Entity> $entities */
    public array $entities;

    public function getEntity(Coordinates $coordinates): Entity
    {
        return $this->entities[$coordinates->convertToKey()];
    }
}
