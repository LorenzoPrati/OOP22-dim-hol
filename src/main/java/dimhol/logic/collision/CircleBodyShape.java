package dimhol.logic.collision;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

public class CircleBodyShape implements BodyShape {

    private double radius;

    public CircleBodyShape(final double radius) {
        this.radius = radius;
    }

    @Override
    public double getBoundingWidth() {
        return 2*radius;
    }

    @Override
    public double getBoundingHeight() {
        return 2*radius;
    }


    @Override
    public Polygon getShape(double x, double y) {
        final GeometricShapeFactory gsf = new GeometricShapeFactory();
        gsf.setNumPoints(4);
        gsf.setSize(2*radius);
        gsf.setCentre(new Coordinate(x+radius, y+radius));
        return gsf.createCircle();
    }
}
