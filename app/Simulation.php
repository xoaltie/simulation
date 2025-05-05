<?php

namespace App;

use App\Actions\Contracts\ActionContract;
use App\Actions\Init\MapPrepareAction;
use App\Infrastructure\Map;
use App\Infrastructure\Renderer;

final class Simulation
{
    private Map $map;
    private Renderer $renderer;

    /** @var array<int, ActionContract> $initActions  */
    private array $initActions = [];
    public function __construct()
    {
        $this->map = Map::genInstance();
        $this->renderer = new Renderer();
    }

    public function start(): void
    {
        $this->prepareInitActions();

        foreach ($this->initActions as $action) {
            $action->make();
        }

        $this->renderer->render($this->map);
    }

    private function prepareInitActions(): void
    {
        $this->initActions[] = new MapPrepareAction($this->map);
    }
}
