<?php

namespace App\Infrastructure;

final class Renderer
{
    public function render(Map $map): void
    {
        $entities = $map->getEntityList();

        for ($i = 0; $i < $map::HEIGHT; $i++) {
            for ($j = 0; $j < $map::WIDTH; $j++) {
                echo $entities[$j]->icon . " ";
            }

            echo PHP_EOL;
        }
    }
}
