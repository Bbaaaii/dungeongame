package dungeonmania.entities.logical;

import dungeonmania.entities.Entity;
import dungeonmania.entities.logical.logicalRules.LogicalRule;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class LightBulb extends Entity {
    private LogicalRule rule;

    public LightBulb(Position position, LogicalRule rule) {
        super(position);
        this.rule = rule;
    }

    public boolean isOn() {
        return rule.isActive();
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
