package model;

import java.awt.geom.Point2D;

/**
 * An interface to model a game object
 */
public interface GameObject {
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
    Point2D getPosition();

    /**
     * 
     * @param p is the position to set
     */
    void setPosition(Point2D p);

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
    Type getType();

    /**
     * 
     * @param t the type to be set
     */
    void setType(Type t);

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
}
