package model.rooms;

import java.util.List;

import model.GameObject;
import model.common.ObjectType;

public class NormalRoom extends AbstractRoomImpl {

    @Override
    public void update(long dt) {
        if (this.getRoomObjects().stream()
                .filter(o -> o.getType() == ObjectType.ENEMY_1 
                    || o.getType() == ObjectType.ENEMY_2)
                .toList().isEmpty()) {
            this.spawnReward();
        }
        this.updateObjects(dt);
    }

    public List<GameObject> spawnReward() {
        // TODO Auto-generated method stub
        return null;
    }
    
}
