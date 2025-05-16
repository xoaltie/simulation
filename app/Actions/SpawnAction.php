<?php

namespace App\Actions;

use App\Map;
use App\SimulationSettings;

final class SpawnAction implements Action
{
    public function execute(Map $map): void
    {
        $countEntities = array_count_values(array_map(function ($entity) {
            return $entity::class;
        }, $map->entities));

        foreach ($countEntities as $entity => $count) {
            if (array_key_exists($entity, SimulationSettings::MAX_ENTITIES)
                && $count < SimulationSettings::MAX_ENTITIES[$entity]) {
                $spawnPosition = $map->getSpawnPosition();

                if ($spawnPosition) {
                    $map->spawn($spawnPosition, new $entity());

                }
            }
        }
    }
}
