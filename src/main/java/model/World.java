package model;

import java.util.List;
import model.common.TileType;

/**
 * An interface to model the world where game object exist
 */
public interface World {

    /**
     * 
     * @param c
     */
    //public void setWorldListener(Controller c);

    /**
     * 
     * @param dt is the delta time
     */
    public void update(long dt);
    
    /**
     * 
     * @param id
     * @return the object from the id
     */
    GameObject getGameObject(int id);

    /**
     * 
     * @return all the game objects
     */
    List<GameObject> getGameObjectList();

    /**
     * 
     * @param tilemap is the new map to load
     */
    public void changeRoom(TileType[][] tilemap);

    /**
     * 
     * @return true if the current room is cleared, 
     * false otherwise
     */
    public boolean roomCleared();

    /**
     * 
     * @return true if game ended, false otherwise
     */
    public boolean isFinished();

    /**
     * 
     * @return true if win, false if player death
     */
    public boolean getResult();

}
