package dungeonmania.entities.enemies.snakestuff;


import dungeonmania.entities.Player;
import dungeonmania.entities.enemies.movement.HuntFoodStrategy;
import dungeonmania.entities.enemies.movement.MovementStrategy;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SnakeHead extends Snake {
    private MovementStrategy huntFoodStrategy;

    public SnakeHead(
        Position position, double health, double attack, double arrowBuff, double treasureBuff,
        double keyBuff, Position nextMovePos, Snake nextPart, Snake prevPart, Boolean invis, Boolean invinc
    ) {
        super(
            position, health, attack, arrowBuff, treasureBuff,
            keyBuff, nextMovePos, nextPart, prevPart, invis, invinc
        );
        huntFoodStrategy = new HuntFoodStrategy();
    }

    @Override
    public void move(GameMap map, Player player) {
        setNextAddPos(getPosition());
        if (!map.getEdibles().isEmpty()) {
            huntFoodStrategy.move(map, player, this);
            if (getNextPart() != null) {
                // move the next part
                getNextPart().move(map, player);
                // after it moves, set the next move position of that part to the current position of this part
                getNextPart().setNextMovePos(getPosition());
            }
        }
    }
}
