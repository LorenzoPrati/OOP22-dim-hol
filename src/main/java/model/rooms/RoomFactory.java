package model.rooms;

import java.util.LinkedList;
import java.util.Random;

import model.common.IDUtil;
import model.common.RoomType;

public class RoomFactory {

    private final static int NUM_ENEMIES = 4;

    Random rand = new Random();
    IDUtil idGenerator;

    public RoomFactory(IDUtil idGenerator) {
        this.idGenerator = idGenerator;
    }

    public Room createRoom(RoomType t, Boolean[][] map) {
        switch (t) {
            case BASE:
                return new BaseRoom(new LinkedList<>(), t, false, null);

            default:
                break;
        }
        return null;
    }

}
