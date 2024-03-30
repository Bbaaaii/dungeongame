package dungeonmania.entities.enemies.movement;

import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.Enemy;
import dungeonmania.map.GameMap;

public interface MovementStrategy {
    public void move(GameMap map, Player player, Enemy enemy);
}
