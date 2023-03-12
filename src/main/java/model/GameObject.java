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
     * @param s the state to set.
     */
    void setState(State s);

    /**
     * 
     * @return the current room.
     */
    Room getRoom();
}
