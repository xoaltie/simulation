<?php

namespace App;

final class Node
{
    public function __construct(
        public Coordinates $coordinates,
        public ?Node $previous = null,
        public ?int $cost = 999999,
    ) {}
}
