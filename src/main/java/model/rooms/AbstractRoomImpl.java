package model.rooms;

import java.util.LinkedList;
import java.util.List;
import model.GameObject;
import model.common.RoomType;

public abstract class AbstractRoomImpl implements Room {

    private List<GameObject> gameObjects;
    private RoomType type;
    private boolean cleared;

    public AbstractRoomImpl(List<GameObject> gameObjects, RoomType type, boolean cleared) {
        this.gameObjects = gameObjects;
        this.type = type;
        this.cleared = cleared;
    }

    public abstract void update(long dt);

    public void updateObjects(long dt) {
        var copy = new LinkedList<>(this.gameObjects);
        copy.stream().forEach(o -> o.update(dt));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameObject> getObjects() {
        return this.gameObjects;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoomType getRoomType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void addObjects(List<GameObject> objects) {
        this.gameObjects.addAll(objects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void removeObjects(List<GameObject> objects) {
        this.gameObjects.removeAll(objects);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setCleared() {
        this.cleared = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCleared() {
        return this.cleared;
    }

}
