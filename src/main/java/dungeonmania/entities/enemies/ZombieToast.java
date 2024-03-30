package dungeonmania.entities.enemies;

import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.enemies.movement.FleePlayerStrategy;
import dungeonmania.entities.enemies.movement.MovementStrategy;
import dungeonmania.entities.enemies.movement.RandomMoveStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class ZombieToast extends Enemy {
    public static final double DEFAULT_HEALTH = 5.0;
    public static final double DEFAULT_ATTACK = 6.0;

    private MovementStrategy randomStrategy;
    private MovementStrategy fleeStrategy;

    public ZombieToast(Position position, double health, double attack) {
        super(position, health, attack);

        randomStrategy = new RandomMoveStrategy();
        fleeStrategy = new FleePlayerStrategy();
    }

    @Override
    public void move(GameMap map, Player player) {
        if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            fleeStrategy.move(map, player, this);
        } else {
            randomStrategy.move(map, player, this);
        }

    }

}
