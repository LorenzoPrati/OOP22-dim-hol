package model.physics;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;

/**
 * An interface to model a box for detecting collisions
 */
public interface CollisionBox {

    /**
     * 
     * @param cb the collision box to check collision with
     * @return true if a box intersects the other
     */
    boolean intersects(CollisionBox cb);

    /**
     * translates the box to the given coordinate
     * @param p is the new point 
     */
    void translate(Coordinate c);

    /**
     * 
     * @return the geometry representing the box
     */
   Geometry getBox();

}
