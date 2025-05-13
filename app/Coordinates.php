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

    public static function convertToObject(string $key): self
    {
        $coordinates = explode(",", $key);

        return new Coordinates((int) $coordinates[0], (int) $coordinates[1]);
    }
}
