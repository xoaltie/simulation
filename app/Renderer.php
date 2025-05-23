<?php

namespace App;

final readonly class Renderer
{
    public function render(Map $map, int $stepCount): void
    {
        $this->renderMap($map);
        $this->renderStepCount($stepCount);
    }

    private function renderStepCount(int $stepCount): void
    {
        echo "Количество шагов: " . $stepCount . PHP_EOL . PHP_EOL;
    }

    private function renderMap(Map $map): void
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
