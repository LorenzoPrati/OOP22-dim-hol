package model.dynamic;

import org.locationtech.jts.geom.Coordinate;

import model.common.Direction;

/**
 * An interface to model an object that is capable of movement.
 */
public interface DynamicObject {

    /**
     * 
     * @param dt
     */
    void move(long dt);

    /**
     * 
     * @return
     */
    Direction getDirection();

    /**
     * 
     * @param direction
     */
    void setDirection(Direction direction);

    /**
     * 
     * @return
     */
    double getSpeed();

    /**
     * 
     * @param speed
     */
    void setSpeed(double speed);

    /**
     * 
     * @return
     */
    boolean isMoving();

    /**
     * 
     */
    void enableMovement();

    /**
     * 
     * @return
     */
    boolean isCollidingWithWalls();

    /**
     * 
     * @return
     */
    Coordinate getLastPosition();

    /**
     * 
     * @param lastPosition
     */
    void setLastPosition(Coordinate lastPosition);

    /**
     * 
     */
    void resetMovement();

    /**
     * 
     */
    void bounceBack();

}