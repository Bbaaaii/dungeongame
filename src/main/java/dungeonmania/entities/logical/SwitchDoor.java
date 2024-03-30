package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;
import dungeonmania.entities.enemies.Spider;
import dungeonmania.entities.logical.logicalRules.LogicalRule;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class SwitchDoor extends Entity {
    private LogicalRule rule;

    public SwitchDoor(Position position, LogicalRule rule) {
        super(position);
        this.rule = rule;
    }

    public boolean isOpen() {
        return rule.isActive();
    }

    @Override
    public boolean canMoveOnto(GameMap map, Entity entity) {
        return rule.isActive() || entity instanceof Spider;
    }

    @Override
    public void resetLogic(GameMap map) {
        rule.reset(map, this);
    }

    @Override
    public void activateLogic(GameMap map) {
        rule.setActive();
    }
}
