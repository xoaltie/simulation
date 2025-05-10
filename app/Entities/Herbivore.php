<?php

namespace App\Entities;

final class Herbivore extends Creature
{
    public function makeMove(): void
    {
        // TODO: Implement makeMove() method.
    }

    public function getSprite(): string
    {
        return "\u{1F404}";
    }
}
