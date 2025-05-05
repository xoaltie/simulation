<?php

namespace App\Actions\Init;

use App\Actions\Contracts\ActionContract;
use App\Creatures\Herbivore;
use App\Creatures\Predator;
use App\Infrastructure\Map;
use App\Resources\Grass;
use App\Statics\Rock;
use App\Statics\Tree;

final class MapPrepareAction implements ActionContract
{
    /**
     * @var array<int, class-string> $entityClasses
     */
    private array $entityClasses;

    public function __construct(
        private Map $map,
    ) {
        $this->entityClasses = [
            Herbivore::class,
            Predator::class,
            Grass::class,
            Rock::class,
            Tree::class,
        ];
    }

    public function make(): void
    {
        $entityList = [];

        for ($i = 0; $i < $this->map::HEIGHT * $this->map::WIDTH; $i++) {
            $entityList[] = new $this->entityClasses[array_rand($this->entityClasses)]();
        }

        $this->map->setEntityList($entityList);
    }
}
