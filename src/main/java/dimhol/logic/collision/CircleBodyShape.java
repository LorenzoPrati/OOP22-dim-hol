package dimhol.logic.collision;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.math.Vector2D;
import org.locationtech.jts.util.GeometricShapeFactory;

/**
 * Models a rectangle shape.
 */
public class CircleBodyShape implements BodyShape {

    /**
     * The radius of the circle.
     */
    private final double radius;

    /**
     * Constructs a circle shape.
     * @param radius the radius to assign the shape
     */
    public CircleBodyShape(final double radius) {
        this.radius = radius;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoundingWidth() {
        return this.radius * 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoundingHeight() {
        return this.radius * 2;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Polygon computeShape(final Vector2D vec) {
        final GeometricShapeFactory gsf = new GeometricShapeFactory();
        gsf.setNumPoints(4);
        gsf.setSize(2 * radius);
        gsf.setCentre(new Coordinate(vec.getX() + radius, vec.getY() + radius));
        return gsf.createCircle();
    }
}
