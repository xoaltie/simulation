<?php

namespace App\Infrastructure;

final class Map
{
    public const int WIDTH = 20;
    public const int HEIGHT = 20;
    private static ?Map $instance = null;

    /**
     * @propery array<mixed> $entityList
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
     * @return array
     */
    public function getEntityList(): array
    {
        return $this->entityList;
    }

    /**
     * @param array $entityList
     * @return void
     */
    public function setEntityList(array $entityList): void
    {
        $this->entityList = $entityList;
    }
}
