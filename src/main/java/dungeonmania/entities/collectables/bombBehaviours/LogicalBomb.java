package dungeonmania.entities.collectables.bombBehaviours;

import dungeonmania.entities.collectables.Bomb;
import dungeonmania.entities.logical.logicalRules.LogicalRule;
import dungeonmania.map.GameMap;

public class LogicalBomb implements BombBehaviour {
    private Bomb bomb;
    private LogicalRule rule;

    public LogicalBomb(Bomb bomb, LogicalRule rule) {
        this.bomb = bomb;
        this.rule = rule;
        return;
    }

    public void onPickup() {
        return;
    }

    public void onPutDown(GameMap map) {
        return;
    }

    public void reset(GameMap map) {
        rule.reset(map, bomb);
    }

    public void setActive(GameMap map) {
        rule.setActive();
        if (rule.isActive()) {
            bomb.explode(map);
        }
    }
}
