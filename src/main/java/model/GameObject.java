package model;

import model.common.P2d;
import model.common.State;
import model.common.ObjectType;
import model.physics.CollisionBox;
import model.rooms.Room;

/**
 * An interface to model a game object
 */
public interface GameObject {

    /**
     * 
     * @param dt is the delta time to advance
     */
    void update(long dt);

    /**
     * 
     * @return the id of the game object
     */
    int getId();

    /**
     * 
     * @param id is the id to be set
     */
    void setId(int id);

    /**
     * 
     * @return the current position
     */
    P2d getPosition();

    /**
     * 
     * @param p is the position to set
     */
    void setPosition(P2d p);

    /**
     * 
     * @return the box for deceting collisions
     */
    CollisionBox getCollisionBox();

    /**
     * 
     * @param cb the box to be set
     */
    void setCollisionBox(CollisionBox cb);

    /**
     * 
     * @return the game object type 
     */
    ObjectType getType();

    /**
     * 
     * @param t the type to be set
     */
    void setType(ObjectType t);

    /**
     * 
     * @return the game object state
     */
    State getState();

    /**
     * 
     * @param s the state to be set
     */
    void setState(State s);

    /**
     * 
     * @return
     */
    Room getRoom();
}
