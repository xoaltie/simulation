<?php

namespace App\Actions;

use App\Coordinates;
use App\Entities\Grass;
use App\Entities\Herbivore;
use App\Entities\Predator;
use App\Entities\Rock;
use App\Entities\Tree;
use App\Map;

final class MapSetupAction implements Action
{
    public function execute(Map $map): void
    {
        $map->spawn(new Coordinates(5, 2), new Tree());
        $map->spawn(new Coordinates(1, 8), new Tree());
        $map->spawn(new Coordinates(0, 2), new Grass());
        $map->spawn(new Coordinates(5, 7), new Grass());
        $map->spawn(new Coordinates(8, 1), new Grass());
        $map->spawn(new Coordinates(1, 5), new Predator());
        $map->spawn(new Coordinates(5, 3), new Predator());
        $map->spawn(new Coordinates(8, 8), new Herbivore());
        $map->spawn(new Coordinates(2, 1), new Herbivore());
        $map->spawn(new Coordinates(0, 9), new Herbivore());
        $map->spawn(new Coordinates(9, 2), new Herbivore());
        $map->spawn(new Coordinates(4, 4), new Rock());
        $map->spawn(new Coordinates(8, 7), new Rock());
    }
}
