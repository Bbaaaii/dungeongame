package dungeonmania.entities.logical.logicalRules;

import dungeonmania.entities.Entity;
import dungeonmania.map.GameMap;

public class CoAndRule implements LogicalRule {
    private boolean synced = true;
    private int numRequired = 2;
    private int numActive = 0;

    public void setActive() {
        numActive++;
    }

    public void reset(GameMap map, Entity entity) {
        // Keep track of whether we're synced
        // We're synced if all the wires were off
        // If all the wires are on and synced, we stay synced
        synced = (numActive == 0) || ((numActive == numRequired) && synced);

        numRequired = Math.max(2, LogicalRule.numAdjacentConductors(map, entity.getPosition()));
        numActive = 0;
    }

    public boolean isActive() {
        return (numActive == numRequired) && synced;
    }

}
