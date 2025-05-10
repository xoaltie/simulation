<?php

namespace App;

final class AStar
{
    /**
     * @return array<int, Node>|false
     */
    public static function findPath(Coordinates $start, Coordinates $target): array|false
    {
        /** @var array<int, Node> $reachable */
        $reachable = [
            new Node(coordinates: $start, cost: 0),
        ];
        /** @var array<int, Node> $explored */
        $explored = [];

        while (!empty($reachable)) {
            $node = self::chooseNode($target, $reachable);

            if ($node === null) {
                return false;
            }

            if ($node->coordinates == $target) {
                return self::buildPath($node);
            }


            unset($reachable[array_search($node, $reachable)]);
            $explored[] = $node;

            $adjacentNode = self::getAdjacentNodes($node);

            $newReachable = array_udiff($adjacentNode, $explored, function (Node $first, Node $second) {
                if ($first->coordinates > $second->coordinates) {
                    return 1;
                } elseif ($first->coordinates < $second->coordinates) {
                    return -1;
                } else {
                    return 0;
                }
            });

            $reachable = self::findNewReachable($newReachable, $reachable, $node);
        }

        return false;
    }

    /**
     * @param Coordinates $target
     * @param array<int, Node> $reachable
     * @return Node|null
     */
    private static function chooseNode(Coordinates $target, array $reachable): Node|null
    {
        $bestNode = null;
        $minCost = 999999;

        foreach ($reachable as $node) {
            $costStartToNode = $node->cost;
            $costNodeToGoal = self::estimateDistance($node->coordinates, $target);
            $totalCost = $costStartToNode + $costNodeToGoal;

            if ($minCost > $totalCost) {
                $minCost = $totalCost;
                $bestNode = $node;
            }
        }

        return $bestNode;
    }

    /**
     * @return array<int, Node>
     */
    private static function buildPath(Node $target): array
    {
        $path = [];

        while ($target !== null) {
            $path[] = $target;

            $target = $target->previous;
        }

        return $path;
    }

    /**
     * @return array<int, Node>
     */
    private static function getAdjacentNodes(Node $node): array
    {
        $adjacentNodes = [];

        if ($node->coordinates->row - 1 >= 0) {
            $adjacentNodes[] = new Node(new Coordinates($node->coordinates->row - 1, $node->coordinates->column));
        }

        if ($node->coordinates->column + 1 <= 9) {
            $adjacentNodes[] = new Node(new Coordinates($node->coordinates->row, $node->coordinates->column + 1));
        }

        if ($node->coordinates->row + 1 <= 9) {
            $adjacentNodes[] = new Node(new Coordinates($node->coordinates->row + 1, $node->coordinates->column));
        }

        if ($node->coordinates->column - 1 >= 0) {
            $adjacentNodes[] = new Node(new Coordinates($node->coordinates->row, $node->coordinates->column - 1));
        }


        return $adjacentNodes;
    }


    /**
     * @param array<int, Node> $newReachable
     * @param array<int, Node> $reachable
     * @param Node $node
     * @return array<int, Node>
     */
    private static function findNewReachable(array $newReachable, array $reachable, Node $node): array
    {
        foreach ($newReachable as $adjacent) {
            if (!self::searchNode($adjacent, $reachable)) {
                $reachable[] = $adjacent;
            }

            if ($node->cost + 1 < $adjacent->cost) {
                $adjacent->previous = $node;
                $adjacent->cost = $node->cost + 1;
            }
        }

        return $reachable;
    }

    private static function estimateDistance(Coordinates $start, Coordinates $target): int
    {
        return abs($start->row - $target->row) + abs($start->column - $target->column);
    }

    /**
     * @param Node $node
     * @param array<int, Node> $array
     * @return bool
     */
    private static function searchNode(Node $node, array $array): bool
    {
        foreach ($array as $value) {
            if ($node->coordinates == $value->coordinates) {
                return true;
            }
        }

        return false;
    }
}
