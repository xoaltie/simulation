<?php

namespace App\Entities;

use App\Map;

final class Herbivore extends Creature
{
    public function makeMove(Map $map): void
    {
        // TODO: Implement makeMove() method.
    }

    public function getSprite(): string
    {
        return "\u{1F404}";
    }
}
