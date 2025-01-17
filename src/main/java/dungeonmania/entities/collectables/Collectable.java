package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.snakestuff.SnakeHead;
import dungeonmania.entities.inventory.InventoryItem;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public abstract class Collectable extends Entity implements InventoryItem {
    public Collectable(Position position) {
        super(position);
    }

    @Override
    public void onOverlap(GameMap map, Entity entity) {
        if (entity instanceof Player) {
            if (!((Player) entity).pickUp(this))
                return;
            map.destroyEntity(this);
        }

        if (entity instanceof SnakeHead && isEdible()) {
            ((SnakeHead) entity).addBody(map);
            map.destroyEntity(this);
            whenEaten(((SnakeHead) entity));
        }
    }

    public void whenEaten(SnakeHead head) {
        return;
    }
}
