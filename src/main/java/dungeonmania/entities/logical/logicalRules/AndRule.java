package dungeonmania.entities.logical.logicalRules;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public class AndRule implements LogicalRule {
    private int numRequired = 2;
    private int numActive = 0;

    public void setActive() {
        numActive++;
    }

    public void reset(GameMap map, Entity entity) {
        numActive = 0;
        numRequired = Math.max(2, LogicalRule.numAdjacentConductors(map, entity.getPosition()));
    }

    public boolean isActive() {
        // There should be no way for numActive to exceed numRequired
        return numActive == numRequired;
    }
}
