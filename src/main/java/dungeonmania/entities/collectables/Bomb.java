package dungeonmania.entities.collectables;

import dungeonmania.util.Position;

import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.collectables.bombBehaviours.BombBehaviour;
import dungeonmania.entities.collectables.bombBehaviours.LogicalBomb;
import dungeonmania.entities.collectables.bombBehaviours.RegularBomb;
import dungeonmania.entities.logical.logicalRules.LogicalRule;
import dungeonmania.map.GameMap;

public class Bomb extends Collectable  {
    public enum State {
        SPAWNED, INVENTORY, PLACED
    }

    public static final int DEFAULT_RADIUS = 1;
    private State state;
    private int radius;

    private BombBehaviour behaviour;

    public Bomb(Position position, int radius) {
        super(position);
        state = State.SPAWNED;
        this.radius = radius;
        behaviour = new RegularBomb(this);
    }

    public Bomb(Position position, int radius, LogicalRule rule) {
        super(position);
        state = State.SPAWNED;
        this.radius = radius;
        behaviour = new LogicalBomb(this, rule);
    }

    public void initPlaced(GameMap map) {
        behaviour.onPutDown(map);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (state != State.SPAWNED)
            return;
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this))
                return;
            behaviour.onPickup();
            map.destroyEntity(this);
        }
        this.state = State.INVENTORY;
    }

    public void onPutDown(GameMap map, Position p) {
        translate(Position.calculatePositionBetween(getPosition(), p));
        map.addEntity(this);
        this.state = State.PLACED;
        behaviour.onPutDown(map);
    }

    public void explode(GameMap map) {
        // TODO make sure to unsubscribe observers from the regular bomb behaviour
        // task 3 moment
        int x = getPosition().getX();
        int y = getPosition().getY();
        for (int i = x - radius; i <= x + radius; i++) {
            for (int j = y - radius; j <= y + radius; j++) {
                List<Entity> entities = map.getEntities(new Position(i, j));
                entities = entities.stream().filter(e -> !(e instanceof Player)).collect(Collectors.toList());
                for (Entity e : entities)
                    map.destroyEntity(e);
            }
        }
    }

    public State getState() {
        return state;
    }

    @Override
    public void resetLogic(GameMap map) {
        behaviour.reset(map);
    }

    @Override
    public void activateLogic(GameMap map) {
        behaviour.setActive(map);
    }

}
