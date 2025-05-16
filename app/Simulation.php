<?php

namespace App;

use App\Actions\Action;
use App\Actions\MapSetupAction;
use App\Actions\MovementAction;
use App\Actions\SpawnAction;

final class Simulation
{
    private Map $map;
    private Renderer $renderer;
    private int $stepCount = 0;

    /** @var array<int, Action> $initActions */
    private array $initActions = [];

    /** @var array<int, Action> $turnActions */
    private array $turnActions = [];

    public function start(): void
    {
        $this->init();

        $this->runInitActions();

        echo "Начать симуляцию?" . PHP_EOL . "Да[Д] или Нет[Н]: ";

        $input = readline();

        while (true) {
            if (mb_strtoupper($input) === 'Д') {
                $this->startSimulation();
            } elseif (mb_strtoupper($input) === 'Н') {
                exit();
            } else {
                echo "Неизвестная команда" . PHP_EOL;
            }
        }
    }

    private function startSimulation(): void
    {
        while (true) {
            $this->runTurnActions();

            $this->stepCount++;

            $this->renderer->render($this->map, $this->stepCount);

            sleep(1);
        }
    }

    private function init(): void
    {
        $this->map = new Map();
        $this->renderer = new Renderer();

        $this->setInitActions();
        $this->setTurnActions();
    }

    private function setInitActions(): void
    {
        $this->initActions[] = new MapSetupAction();
    }

    private function setTurnActions(): void
    {
        $this->turnActions[] = new SpawnAction();
        $this->turnActions[] = new MovementAction();
    }

    private function runInitActions(): void
    {
        foreach ($this->initActions as $action) {
            $action->execute($this->map);
        }
    }

    private function runTurnActions(): void
    {
        foreach ($this->turnActions as $action) {
            $action->execute($this->map);
        }
    }
}
