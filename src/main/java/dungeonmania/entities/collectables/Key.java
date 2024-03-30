package dungeonmania.entities.collectables;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.snakestuff.SnakeHead;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class Key extends Collectable {
    private int number;

    public Key(Position position, int number) {
        super(position);
        this.number = number;
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return true;
    }

    public int getnumber() {
        return number;
    }

    @Override
    public boolean isEdible() {
        return true;
    }

    public void whenEaten(SnakeHead head) {
        head.keyBuffSnake();
    }
}
