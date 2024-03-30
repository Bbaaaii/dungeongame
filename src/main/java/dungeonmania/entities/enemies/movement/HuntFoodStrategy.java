package dungeonmania.entities.enemies.movement;

import java.util.List;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class HuntFoodStrategy implements MovementStrategy {
    public void move(GameMap map, Player player, Enemy enemy) {
        Entity closestFood = calculateClosestFood(map, enemy).get(0);
        Position nextPos = map.dijkstraPathFind(enemy.getPosition(), closestFood.getPosition(), enemy);
        map.moveTo(enemy, nextPos);
    }

    private List<Entity> calculateClosestFood(GameMap map, Entity enemy) {
        List<Entity> edibles = map.getEdibles();
        // sort edibles by distance
        edibles.sort((Entity f1, Entity f2) ->
            getDistance(enemy.getPosition(), f1.getPosition()) - getDistance(enemy.getPosition(), f2.getPosition()));
        return edibles;
    }

    private int getDistance(Position p1, Position p2) {
        return (Math.abs(p1.getX() - p2.getX()) + Math.abs(p1.getY() - p2.getY()));
    }
}
