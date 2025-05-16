<?php

namespace App\Entities;

use App\Coordinates;

abstract class Entity
{
    public Coordinates $position;

    abstract public function getSprite(): string;
}
