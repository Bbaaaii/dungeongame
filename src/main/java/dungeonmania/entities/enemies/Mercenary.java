package dungeonmania.entities.enemies;

import dungeonmania.Game;
import dungeonmania.battles.BattleStatistics;
import dungeonmania.entities.Entity;
import dungeonmania.entities.Interactable;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.Treasure;
import dungeonmania.entities.collectables.potions.InvincibilityPotion;
import dungeonmania.entities.collectables.potions.InvisibilityPotion;
import dungeonmania.entities.enemies.movement.FleePlayerStrategy;
import dungeonmania.entities.enemies.movement.HuntPlayerStrategy;
import dungeonmania.entities.enemies.movement.MovementStrategy;
import dungeonmania.entities.enemies.movement.ProtectPlayerStrategy;
import dungeonmania.entities.enemies.movement.RandomMoveStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Mercenary extends Enemy implements Interactable {
    public static final int DEFAULT_BRIBE_AMOUNT = 1;
    public static final int DEFAULT_BRIBE_RADIUS = 1;
    public static final double DEFAULT_ATTACK = 5.0;
    public static final double DEFAULT_HEALTH = 10.0;

    private int bribeAmount = Mercenary.DEFAULT_BRIBE_AMOUNT;
    private int bribeRadius = Mercenary.DEFAULT_BRIBE_RADIUS;

    private double allyAttack;
    private double allyDefence;
    private boolean allied = false;
    private boolean adjacentToPlayer = false;

    private MovementStrategy huntStrategy;
    private MovementStrategy fleeStrategy;
    private MovementStrategy protectStrategy;
    private MovementStrategy randomStrategy;

    public Mercenary(Position position, double health, double attack, int bribeAmount, int bribeRadius,
            double allyAttack, double allyDefence) {
        super(position, health, attack);
        this.bribeAmount = bribeAmount;
        this.bribeRadius = bribeRadius;
        this.allyAttack = allyAttack;
        this.allyDefence = allyDefence;

        huntStrategy = new HuntPlayerStrategy();
        fleeStrategy = new FleePlayerStrategy();
        protectStrategy = new ProtectPlayerStrategy();
        randomStrategy = new RandomMoveStrategy();

    }

    public boolean isAllied() {
        return allied;
    }

    public boolean isAdjacentToPlayer() {
        return adjacentToPlayer;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (allied)
            return;
        super.onOverlap(map, entity);
    }

    /**
     * check whether the current merc can be bribed
     * @param player
     * @return
     */
    private boolean canBeBribed(Player player) {
        return bribeRadius >= 0 && player.countEntityOfType(Treasure.class) >= bribeAmount;
    }

    /**
     * bribe the merc
     */
    private void bribe(Player player) {
        for (int i = 0; i < bribeAmount; i++) {
            player.use(Treasure.class);
        }

    }

    @Override
    public void interact(Player player, Game game) {
        allied = true;
        bribe(player);
        if (!adjacentToPlayer && Position.isAdjacent(player.getPosition(), getPosition()))
            adjacentToPlayer = true;
    }

    @Override
    public void move(GameMap map, Player player) {
        if (allied) {
            protectStrategy.move(map, player, this);
            if (!adjacentToPlayer && Position.isAdjacent(player.getPosition(), this.getPosition()))
                adjacentToPlayer = true;
        } else if (map.getPlayer().getEffectivePotion() instanceof InvisibilityPotion) {
            // Move random
            randomStrategy.move(map, player, this);
        } else if (map.getPlayer().getEffectivePotion() instanceof InvincibilityPotion) {
            fleeStrategy.move(map, player, this);
        } else {
            // Follow hostile
            huntStrategy.move(map, player, this);
        }
    }

    @Override
    public boolean isInteractable(Player player) {
        return !allied && canBeBribed(player);
    }

    @Override
    public BattleStatistics getBattleStatistics() {
        if (!allied)
            return super.getBattleStatistics();
        return new BattleStatistics(0, allyAttack, allyDefence, 1, 1);
    }
}
