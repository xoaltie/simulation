<?php

namespace App\Entities;

use App\Map;

abstract class Creature extends Entity
{
    abstract public function makeMove(Map $map): void;
}
