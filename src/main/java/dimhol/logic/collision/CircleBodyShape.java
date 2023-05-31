package dimhol.logic.collision;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.util.GeometricShapeFactory;

public class CircleBodyShape implements BodyShape {

    private final double diameter;

    public CircleBodyShape(final double radius) {
        this.diameter = radius;
    }

    @Override
    public double getBoundingWidth() {
        return diameter;
    }

    @Override
    public double getBoundingHeight() {
        return diameter;
    }


    @Override
    public Polygon getShape(double x, double y) {
        final GeometricShapeFactory gsf = new GeometricShapeFactory();
        gsf.setNumPoints(4);
        gsf.setSize(diameter);
        gsf.setCentre(new Coordinate(x+(diameter / 2), y+(diameter / 2)));
        return gsf.createCircle();
    }
}
