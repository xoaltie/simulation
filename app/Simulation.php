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

        do {
            echo "Начать симуляцию?" . PHP_EOL . "Да[Д] или Нет[Н]: ";
            $input = readline();

            if (mb_strtoupper($input) === 'Д') {
                echo "Выберите режим: 1 - бесконечный, 2 - пошаговый: ";

                $input = readline();

                if (mb_strtoupper($input) === '1') {
                    $this->renderer->render($this->map, $this->stepCount);

                    $this->startSimulation();
                }

                if (mb_strtoupper($input) === '2') {
                    $this->renderer->render($this->map, $this->stepCount);

                    $this->playStep();
                }

            } elseif (mb_strtoupper($input) === 'Н') {
                exit();
            } else {
                echo "Неизвестная команда" . PHP_EOL;
            }
        } while (true);

    }

    private function startSimulation(): void
    {
        while (true) {
            foreach ($this->turnActions as $action) {
                $action->execute($this->map);
            }

            $this->stepCount++;

            $this->renderer->render($this->map, $this->stepCount);

            sleep(1);
        }
    }

    private function playStep(): void
    {
        while (true) {
            echo "Нажмите \"Д\" чтобы сделать ход: ";

            $input = readline();

            if (mb_strtoupper($input) !== 'Д') {
                echo "Неизвестная команда" . PHP_EOL;
                continue;
            }

            foreach ($this->turnActions as $action) {
                $action->execute($this->map);
            }

            $this->stepCount++;

            $this->renderer->render($this->map, $this->stepCount);
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
}
