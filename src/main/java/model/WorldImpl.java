package model;

import java.util.List;
import model.common.TileType;
import model.rooms.Room;

public class WorldImpl implements World {

    private Room currentRoom;

    @Override
    public void update(long dt) {
        this.currentRoom.update(dt);
    }

    @Override
    public GameObject getGameObject(int id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGameObject'");
    }

    @Override
    public List<GameObject> getGameObjectList() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGameObjectList'");
    }

    @Override
    public void changeRoom(TileType[][] tilemap) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'changeRoom'");
    }

    @Override
    public boolean roomCleared() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'roomCleared'");
    }

    @Override
    public boolean isFinished() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'isFinished'");
    }

    @Override
    public boolean getResult() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getResult'");
    }
    
}
