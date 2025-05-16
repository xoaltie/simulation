<?php

namespace App;

use App\Entities\Grass;
use App\Entities\Herbivore;
use App\Entities\Predator;

final readonly class SimulationSettings
{
    public const array MAX_ENTITIES = [
        Grass::class => 4,
        Herbivore::class => 4,
        Predator::class => 2,
    ];
}
