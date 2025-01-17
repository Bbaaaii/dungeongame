package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.snakestuff.SnakeHead;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Arrow extends Collectable {
    public Arrow(Position position) {
        super(position);
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    @Override
    public boolean isEdible() {
        return true;
    }

    @Override
    public void whenEaten(SnakeHead head) {
        head.arrowBuffSnake();
    }
}
