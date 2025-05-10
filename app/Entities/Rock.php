<?php

namespace App\Entities;

final class Rock extends Entity
{
    public function getSprite(): string
    {
        return "\u{1FAA8}";
    }
}
