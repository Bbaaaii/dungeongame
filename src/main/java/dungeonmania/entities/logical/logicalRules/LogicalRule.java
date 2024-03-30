package dungeonmania.entities.logical.logicalRules;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.entities.logical.Wire;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public interface LogicalRule {
    public void setActive();
    public void reset(GameMap map, Entity entity);
    public boolean isActive();

    public static int numAdjacentConductors(GameMap map, Position position) {
        int numAdjacent = 0;
         for (Position p : position.getCardinallyAdjacentPositions()) {
            if (map.getEntities(p).stream().anyMatch(e -> e instanceof Wire || e instanceof Switch)) {
                numAdjacent++;
            }
        }
        return numAdjacent;
    }
}
