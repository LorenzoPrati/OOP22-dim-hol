package model.physics;

import java.awt.geom.Point2D;

/**
 * An interface to model a boundary box 
 * for detecing collision between game objects
 */
public interface CollisionBox {

    /**
     * 
     * @param cb the collision box to check collision with
     * @return true if a box overlaps the other
     */
    boolean overlaps(CollisionBox cb);

    /**
     * translates the box to the given point
     * @param p is the new point 
     */
    void translate(Point2D p);

}
