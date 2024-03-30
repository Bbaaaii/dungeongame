package dungeonmania.entities.enemies.movement;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.entities.enemies.Mercenary;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ProtectPlayerStrategy implements MovementStrategy {
    public void move(GameMap map, Player player, Enemy enemy) {
        boolean adjacentToPlayer = ((Mercenary) enemy).isAdjacentToPlayer();
        Position nextPos = adjacentToPlayer ? player.getPreviousDistinctPosition()
                : map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);

        map.moveTo(enemy, nextPos);
    }
}
