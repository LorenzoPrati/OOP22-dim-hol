package dimhol.logic.collision;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.math.Vector2D;
import org.locationtech.jts.util.GeometricShapeFactory;

/**
 * Models a rectangular shape.
 */
public class RectBodyShape implements BodyShape {

    /**
     * The vertices of the rectangle.
     */
    public static final int VERTICES = 4;
    /**
     * The width of the rectangle.
     */
    private final double width;
    /**
     * The height of the rectangle.
     */
    private final double height;

    /**
     * Constructs a rectangular shape.
     *
     * @param width the width to assign the rectangle
     * @param height the height to assign the rectangle
     */
    public RectBodyShape(final double width, final double height) {
        this.width = width;
        this.height = height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoundingWidth() {
        return this.width;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getBoundingHeight() {
        return this.height;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Polygon computeShape(final Vector2D vec) {
        final GeometricShapeFactory gsf = new GeometricShapeFactory();
        gsf.setNumPoints(VERTICES);
        gsf.setWidth(width);
        gsf.setHeight(height);
        gsf.setBase(new Coordinate(vec.getX(), vec.getY()));
        return gsf.createRectangle();
    }
}
