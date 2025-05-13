<?php

namespace App\Actions;

use App\Map;

interface Action
{
    public function execute(Map $map): void;
}
