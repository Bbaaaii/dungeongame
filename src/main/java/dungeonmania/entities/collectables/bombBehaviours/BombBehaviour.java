package dungeonmania.entities.collectables.bombBehaviours;

import dungeonmania.map.GameMap;

public interface BombBehaviour {
    public void onPickup();
    public void onPutDown(GameMap map);
    public void reset(GameMap map);
    public void setActive(GameMap map);
}
