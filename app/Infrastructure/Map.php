<?php

namespace App\Infrastructure;

use App\Core\Entity;

final class Map
{
    public const int WIDTH = 20;
    public const int HEIGHT = 10;
    private static ?Map $instance = null;

    /**
     * @var list<Entity> $entityList
     */
    private array $entityList = [];

    private function __construct() {}

    public static function genInstance(): Map
    {
        if (self::$instance === null) {
            self::$instance = new Map();
        }

        return self::$instance;
    }

    /**
     * @return list<Entity>
     */
    public function getEntityList(): array
    {
        return $this->entityList;
    }

    /**
     * @param list<Entity> $entityList
     * @return void
     */
    public function setEntityList(array $entityList): void
    {
        $this->entityList = $entityList;
    }
}
