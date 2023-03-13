package model.rooms;

import java.util.List;

import model.GameObject;
import model.common.RoomType;

/**
 * An interface to model a generic room
 */
public interface Room {

    /**
     * update the room
     * @param dt is the delta time
     */
    public void update(long dt);

    /**
     * 
     * @return the object in the room
     */
    public List<GameObject> getObjects();

    /**
     * 
     * @return the type of the room
     */
    RoomType getRoomType();

    /**
     * 
     * @param o 
     */
    public void addObjects(List<GameObject> o);

    /**
     * 
     * @param o
     */
    public void removeObjects(List<GameObject> o);

    /**
     * set the room as cleared
     */
    public void setCleared();

    /**
     * 
     * @return true if the room is cleared, false
     * otherwise
     */
    public boolean isCleared();

}
