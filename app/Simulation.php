<?php

namespace App;

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
        $this->map = new Map();
        $this->renderer = new Renderer();

        $this->init();

        $this->renderer->render($this->map);
    }

    public function init(): void
    {
        $this->map->spawn(new Coordinates(5, 2), new Tree());
        $this->map->spawn(new Coordinates(1, 8), new Tree());
        $this->map->spawn(new Coordinates(0, 2), new Grass());
        $this->map->spawn(new Coordinates(5, 7), new Grass());
        $this->map->spawn(new Coordinates(8, 1), new Grass());
        $this->map->spawn(new Coordinates(1, 5), new Predator());
        $this->map->spawn(new Coordinates(5, 3), new Predator());
        $this->map->spawn(new Coordinates(8, 8), new Herbivore());
        $this->map->spawn(new Coordinates(2, 1), new Herbivore());
        $this->map->spawn(new Coordinates(0, 9), new Herbivore());
        $this->map->spawn(new Coordinates(9, 2), new Herbivore());
        $this->map->spawn(new Coordinates(4, 4), new Rock());
        $this->map->spawn(new Coordinates(8, 7), new Rock());
    }
}
