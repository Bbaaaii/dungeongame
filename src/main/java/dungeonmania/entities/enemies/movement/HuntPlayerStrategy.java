package dungeonmania.entities.enemies.movement;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class HuntPlayerStrategy implements MovementStrategy {
    public void move(GameMap map, Player player, Enemy enemy) {
        Position nextPos = map.dijkstraPathFind(enemy.getPosition(), player.getPosition(), enemy);
        map.moveTo(enemy, nextPos);
    }

}
