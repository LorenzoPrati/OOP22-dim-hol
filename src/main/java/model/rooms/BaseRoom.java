package model.rooms;

import java.util.List;

import model.GameObject;
import model.common.ObjectType;
import model.common.RoomType;
import model.dynamic.enemies.Enemy;
import model.items.Item;

public class BaseRoom extends AbstractRoomImpl {

    public BaseRoom(List<GameObject> gameObjects, RoomType type, boolean cleared, List<Item> rewards) {
        super(gameObjects, type, cleared);
    }

    @Override
    public void update(long dt) {
        if (this.getObjects().stream()
                .filter(o -> o instanceof Enemy)
                .toList().isEmpty()) {
            //this.spawnReward();
        }
        this.updateObjects(dt);
    }

    public List<GameObject> spawnReward() {
        //item factory;
        return null;
    }

}
