<?php

namespace App;

use App\Entities\Creature;
use App\Entities\Grass;
use App\Entities\Herbivore;
use App\Entities\Predator;
use App\Entities\Rock;
use App\Entities\Tree;

final class Simulation
{
    private Map $map;
    private Renderer $renderer;

    public function start(): void
    {
        do {
            echo "Начать симуляцию?" . PHP_EOL . "Да[Д] или Нет[Н]: ";
            $input = readline();

            if (mb_strtoupper($input) === 'Д') {
                $this->map = new Map();
                $this->renderer = new Renderer();

                $this->init();

                $this->renderer->render($this->map);

                $this->playStep();
            } elseif (mb_strtoupper($input) === 'Н') {
                exit();
            } else {
                echo "Неизвестная команда" . PHP_EOL;
            }
        } while (true);

    }

    private function playStep(): void
    {
        while (true) {
            echo "Нажмите \"Д\" чтобы сделать ход: ";

            $input = readline();

            if (mb_strtoupper($input) !== 'Д') {
                echo "Неизвестная команда" . PHP_EOL;
                continue;
            }

            foreach ($this->map->entities as $entity) {
                if ($entity instanceof Creature) {
                    $entity->makeMove($this->map);
                }
            }

            $this->renderer->render($this->map);
        }

    }

    private function init(): void
    {
        $this->map->spawn(new Coordinates(5, 2), new Tree());
        $this->map->spawn(new Coordinates(1, 8), new Tree());
        $this->map->spawn(new Coordinates(0, 2), new Grass());
        $this->map->spawn(new Coordinates(5, 7), new Grass());
        $this->map->spawn(new Coordinates(8, 1), new Grass());
        $this->map->spawn(new Coordinates(1, 5), new Predator());
        $this->map->spawn(new Coordinates(5, 3), new Predator());
        $this->map->spawn(new Coordinates(8, 8), new Herbivore());
        //        $this->map->spawn(new Coordinates(2, 1), new Herbivore());
        //        $this->map->spawn(new Coordinates(0, 9), new Herbivore());
        //        $this->map->spawn(new Coordinates(9, 2), new Herbivore());
        $this->map->spawn(new Coordinates(4, 4), new Rock());
        $this->map->spawn(new Coordinates(8, 7), new Rock());
    }
}
