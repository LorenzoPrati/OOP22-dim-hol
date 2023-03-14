package model;

import java.util.List;
import java.util.Optional;

import model.common.RoomType;
import model.dynamic.player.Player;

/**
 * An interface to model the world.
 */
public interface World {

    /**
     * 
     * @param dt is the delta time.
     */
    public void update(long dt);
    
    /**
     * 
     * @param id
     * @return the object matching the given id
     */
    Optional<GameObject> getGameObject(Integer id);

    /**
     * 
     * @return game objects
     */
    List<GameObject> getGameObjects();

    /**
     * 
     * @return the player
     */
    public Player getPlayer();

    /**
     * 
     * @param tm the tilemap to build the new room
     */
    public void changeRoom(Boolean[][] tm);

    /**
     * 
     * @return true if the current room is cleared
     */
    public boolean isCleared();

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

    /**
     * 
     * @return
     */
    public RoomType getNextRoomType();

}
