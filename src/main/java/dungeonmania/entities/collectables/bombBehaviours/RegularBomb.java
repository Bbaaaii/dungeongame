package dungeonmania.entities.collectables.bombBehaviours;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import dungeonmania.entities.Entity;
import dungeonmania.entities.Switch;
import dungeonmania.entities.collectables.Bomb;
import dungeonmania.map.GameMap;
import dungeonmania.util.Position;

public class RegularBomb implements BombBehaviour {
    private Bomb bomb;

    private List<Switch> subs = new ArrayList<>();

    public RegularBomb(Bomb bomb) {
        this.bomb = bomb;
    }

    public void subscribe(Switch s) {
        this.subs.add(s);
    }

    public void notify(GameMap map) {
        bomb.explode(map);
    }

    public void onPickup() {
        subs.stream().forEach(s -> s.unsubscribe(this));
    }

    public void onPutDown(GameMap map) {
        List<Position> adjPosList = bomb.getPosition().getCardinallyAdjacentPositions();
        adjPosList.stream().forEach(node -> {
            List<Entity> entities = map.getEntities(node).stream().filter(e -> (e instanceof Switch))
                    .collect(Collectors.toList());
            entities.stream().map(Switch.class::cast).forEach(s -> s.subscribe(this, map));
            entities.stream().map(Switch.class::cast).forEach(s -> this.subscribe(s));
        });
    }

    public void reset(GameMap map) {
        return;
    }

    public void setActive(GameMap map) {
        return;
    }
}
