<?php

namespace App\Actions;

use App\Coordinates;
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
                $map->spawn($this->getSpawnPosition($map), new $entity());
            }
        }
    }

    private function getSpawnPosition(Map $map): Coordinates|false
    {
        do {
            $coordinates = new Coordinates(random_int(0, Map::HEIGHT - 1), random_int(0, Map::WIDTH - 1));

            if ($map->isEmptyCell($coordinates)) {
                return $coordinates;
            }

        } while (!$map->isEmptyCell($coordinates));

        return false;
    }
}
