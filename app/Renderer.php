<?php

namespace App;

final readonly class Renderer
{
    public function render(Map $map): void
    {
        for ($i = 0; $i < Map::HEIGHT; $i++) {
            for ($j = 0; $j < Map::WIDTH; $j++) {
                if ($this->isEmptyCell($map, new Coordinates($i, $j))) {
                    echo " " . $this->getEmptyCell() . " ";

                    continue;
                }

                echo "  " . $map->entities[(new Coordinates($i, $j))->convertToKey()]->getSprite() . "  ";
            }

            echo PHP_EOL;
        }
    }

    private function isEmptyCell(Map $map, Coordinates $coordinates): bool
    {
        return !array_key_exists($coordinates->convertToKey(), $map->entities);
    }

    private function getEmptyCell(): string
    {
        return "____";
    }
}
