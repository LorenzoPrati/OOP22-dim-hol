package model;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import org.locationtech.jts.geom.Coordinate;

import model.common.IDUtil;
import model.common.ObjectType;
import model.common.RoomType;
import model.common.State;
import model.dynamic.player.Player;
import model.dynamic.player.PlayerImpl;
import model.physics.CollisionBoxImpl;
import model.rooms.Room;
import model.rooms.RoomFactory;

/**
 * World implementation.
 */
public class WorldImpl implements World {

    private final static int TOTAL_ROOM_NUMBER = 10;
    private final static int SHOP_ROOM_OCCURENCE = 3;

    private Room currentRoom;
    private int roomsCleared = 0;
    IDUtil idGenerator = new IDUtil();
    RoomFactory rf = new RoomFactory(idGenerator);

    public WorldImpl() {

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
    public void changeRoom(Boolean[][] map) {
        this.currentRoom = this.rf.createRoom(this.getNextRoomType(), map);
        this.idGenerator.reset();
        if (this.roomsCleared != 0) {
            var p = (GameObject) this.getPlayer();
            p.setId(this.idGenerator.generateID());
            this.currentRoom.addObjects(Stream.of(p).toList());
        } else {
            this.currentRoom.addObjects(Stream.of((GameObject) new PlayerImpl(new Coordinate(10, 10),
                    this.idGenerator.generateID(), ObjectType.PLAYER, State.IDLE_DOWN,
                    new CollisionBoxImpl(new Coordinate(10, 10)), this.currentRoom, 1, 1)).toList());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCleared() {
        return this.currentRoom.isCleared() || this.roomsCleared == 0;
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
    public RoomType getNextRoomType() {
        return this.roomsCleared == TOTAL_ROOM_NUMBER - 1 ? RoomType.BOSS
                : (this.roomsCleared % SHOP_ROOM_OCCURENCE == 0 ? RoomType.SHOP : RoomType.BASE);
    }

}
