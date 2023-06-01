package dimhol.logic.collision;

import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.math.Vector2D;

/**
 * Represents a generic geometric shape.
 */
public interface BodyShape {

    /**
     * Computes the shape with the given position.
     *
     * @param vec the vector representing the upper left coordinate
     * @return the polygon representing the shape
     */
    Polygon computeShape(Vector2D vec);

    /**
     * Gets the bounding width of the shape.
     * @return the bounding width
     */
    double getBoundingWidth();

    /**
     * Gets the bounding height of the shape.
     * @return the bounding height
     */
    double getBoundingHeight();
}
