<?php

namespace App;

final readonly class Renderer
{
    public function render(Map $map): void
    {
        echo PHP_EOL;

        for ($i = 0; $i < Map::HEIGHT; $i++) {
            for ($j = 0; $j < Map::WIDTH; $j++) {
                if ($map->isEmptyCell(new Coordinates($i, $j))) {
                    echo " " . $this->getEmptyCell() . " ";

                    continue;
                }

                echo "  " . $map->entities[(new Coordinates($i, $j))->convertToKey()]->getSprite() . "  ";
            }

            echo PHP_EOL;
        }

        echo PHP_EOL;
    }

    private function getEmptyCell(): string
    {
        return "____";
    }
}
