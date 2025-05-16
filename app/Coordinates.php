<?php

namespace App;

final class Coordinates
{
    public function __construct(
        public int $row,
        public int $column,
    ) {}

    public function convertToKey(): string
    {
        return "{$this->row},{$this->column}";
    }
}
