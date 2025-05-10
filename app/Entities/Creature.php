<?php

namespace App\Entities;

use App\Map;
use App\Node;

abstract class Creature extends Entity
{
    public int $health;
    public int $speed;

    /** @var ?array<int, Node> $pathToTarget */
    public ?array $pathToTarget;

    abstract public function makeMove(Map $map): void;
}
