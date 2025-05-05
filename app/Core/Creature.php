<?php

namespace App\Core;

abstract class Creature extends Entity
{
    abstract public function makeMove(): void;
}
