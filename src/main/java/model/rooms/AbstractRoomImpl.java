package model.rooms;

import java.util.LinkedList;
import java.util.List;
import model.GameObject;
import model.common.RoomType;

public abstract class AbstractRoomImpl implements Room {

    private List<GameObject> gameObjects;
    private RoomType type;
    private boolean cleared;

    public abstract void update(long dt);

    public void updateObjects(long dt) {
        var copy = new LinkedList<>(this.gameObjects);
        copy.stream().forEach(o -> o.update(dt));
    }

    @Override
    public List<GameObject> getRoomObjects() {
        return this.gameObjects;
    }

    @Override
    public RoomType getRoomType() {
        return this.type;
    }

    @Override
    public void addObjects(List<GameObject> objects) {
        this.gameObjects.addAll(objects);
    }

    @Override
    public void removeObject(GameObject object) {
        this.gameObjects.remove(object);
    }

    @Override
    public void setCleared() {
        this.cleared = true;
    }

    @Override
    public boolean isCleared() {
        return this.cleared;
    }
    
}
