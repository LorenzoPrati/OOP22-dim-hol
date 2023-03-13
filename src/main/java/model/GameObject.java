package model;

import org.locationtech.jts.geom.Coordinate;

import model.common.ObjectType;
import model.common.State;
import model.physics.CollisionBox;
import model.rooms.Room;

/**
 * An interface to model a game object.
 */
public interface GameObject {

    /**
     * 
     * @param dt is the delta time.
     */
    void update(long dt);

    /**
     * 
     * @return the id of the game object.
     */
    Integer getId();

    /**
     * 
     * @return the current position.
     */
    Coordinate getPosition();

    /**
     * 
     * @param c the position to set.
     */
    void setPosition(Coordinate c);

    /**
     * 
     * @return the collision box.
     */
    CollisionBox getCollisionBox();

    /**
     * 
     * @return the game object type.
     */
    ObjectType getType();

    /**
     * 
     * @return the game object state.
     */
    State getState();

    /**
     * 
     * @return the current room.
     */
    Room getRoom();

    /**
     * 
     * @return the height of the object
     */
    int getHeight();

    /**
     * 
     * @return the width of the object
     */
    int getWidth();

}
