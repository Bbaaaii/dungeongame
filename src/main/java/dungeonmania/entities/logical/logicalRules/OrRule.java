package dungeonmania.entities.logical.logicalRules;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public class OrRule implements LogicalRule {
    private int numActive = 0;

    public void setActive() {
        numActive++;
    }

    public void reset(GameMap map, Entity entity) {
        numActive = 0;
    }

    public boolean isActive() {
        return numActive > 0;
    }
}
