<?php

namespace App\Entities;

final class Grass extends Entity
{
    public function getSprite(): string
    {
        return "\u{1F33F}";
    }
}
