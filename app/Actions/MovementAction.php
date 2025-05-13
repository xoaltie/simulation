<?php

namespace App\Actions;

use App\Entities\Creature;
use App\Map;

final class MovementAction implements Action
{
    public function execute(Map $map): void
    {
        foreach ($map->entities as $entity) {
            if ($entity instanceof Creature) {
                $entity->makeMove($map);
            }
        }
    }
}
