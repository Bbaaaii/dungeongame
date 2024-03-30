package dungeonmania.entities.enemies;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.movement.CircleSpawnStrategy;
import dungeonmania.entities.enemies.movement.MovementStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Spider extends Enemy {
    private MovementStrategy circleSpawnStrategy;

    public static final int DEFAULT_SPAWN_RATE = 0;
    public static final double DEFAULT_ATTACK = 5;
    public static final double DEFAULT_HEALTH = 10;

    public Spider(Position position, double health, double attack) {
        super(position.asLayer(Entity.DOOR_LAYER + 1), health, attack);
        circleSpawnStrategy = new CircleSpawnStrategy(position);
    };

    @Override
    public void move(GameMap map, Player player) {
        circleSpawnStrategy.move(map, player, this);
    }
}
