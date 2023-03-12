package model;

import java.util.List;
import java.util.Optional;

import model.common.RoomType;
import model.dynamic.Player;
import model.rooms.Room;
import model.rooms.RoomMap;

public class WorldImpl implements World {

    private final static int TOTAL_ROOMS_NUMBER = 10;

    private Room currentRoom;
    private int roomExplored;

    public WorldImpl(Room currentRoom, int roomExplored) {
        this.currentRoom = currentRoom;
        this.roomExplored = roomExplored;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(long dt) {
        this.currentRoom.update(dt);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<GameObject> getGameObject(Integer id) {
        return this.currentRoom.getObjects().stream().filter(o -> o.getId().equals(id)).findFirst();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<GameObject> getGameObjects() {
        return this.currentRoom.getObjects();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Player getPlayer() {
        return (Player) this.currentRoom.getObjects().stream().filter(o -> o instanceof Player).findAny().get();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateMap(RoomMap map) {
        
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCleared() {
        return this.currentRoom.isCleared();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isFinished() {
        return this.getPlayer() == null
                || (this.currentRoom.getRoomType().equals(RoomType.BOSS) && this.currentRoom.isCleared());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean getResult() {
        return this.getPlayer() != null ? true : false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public RoomType getRoomMap() {
        return this.roomExplored == TOTAL_ROOMS_NUMBER - 1 ? RoomType.BOSS
                : (this.roomExplored % 3 == 0 ? RoomType.SHOP : RoomType.BASE);
    }

}
