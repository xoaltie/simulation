<?php

namespace App\Entities;

abstract class Creature extends Entity
{
    abstract public function makeMove(): void;
}
