<?php

namespace App;

final class AStar
{
    /**
     * @return array<int, Node>|false
     */
    public static function findPath(Map $map, Coordinates $start, string $targetClass): array|false
    {
        /** @var array<int, Node> $reachable */
        $reachable = [
            new Node(coordinates: $start, cost: 0),
        ];
        /** @var array<int, Node> $explored */
        $explored = [];

        $targetCoordinates = self::getTargetCoordinates($map, $targetClass);

        $target = self::chooseNearTarget($start, $targetCoordinates);

        $entities = array_filter(array_map(function ($entity) {
            return new Node($entity->position);
        }, $map->entities), function ($entity) use ($target) {
            return !($entity->coordinates === $target);
        });

        sort($entities);

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

            $newReachable = array_udiff($adjacentNode, $explored, $entities, function (Node $first, Node $second) {
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
     * @param Map $map
     * @param string $targetClass
     * @return array<int, Coordinates>
     */
    private static function getTargetCoordinates(Map $map, string $targetClass): array
    {
        $targetCoordinates = [];

        foreach ($map->entities as $entity) {
            if ($entity instanceof $targetClass) {
                $targetCoordinates[] = $entity->position;
            }
        }

        return $targetCoordinates;
    }

    /**
     * @param Coordinates $start
     * @param array<int, Coordinates> $targetCoordinates
     * @return Coordinates
     */
    private static function chooseNearTarget(Coordinates $start, array $targetCoordinates): Coordinates
    {
        $minDistance = 999999;
        $targetPosition = null;

        foreach ($targetCoordinates as $coordinates) {
            $costDistance = self::estimateDistance($start, $coordinates);

            if ($costDistance < $minDistance) {
                $minDistance = $costDistance;
                $targetPosition = $coordinates;
            }
        }

        return $targetPosition;
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
            $path[] = $target->coordinates;

            $target = $target->previous;
        }

        array_pop($path);

        return array_reverse($path);
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

        if ($node->coordinates->column + 1 < Map::WIDTH) {
            $adjacentNodes[] = new Node(new Coordinates($node->coordinates->row, $node->coordinates->column + 1));
        }

        if ($node->coordinates->row + 1 < Map::HEIGHT) {
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
