package it.unibo.dimhol.commons.shapes;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Polygon;
import org.locationtech.jts.shape.GeometricShapeBuilder;
import org.locationtech.jts.util.GeometricShapeFactory;

public class RectBodyShape implements BodyShape {

    public final static int VERTICES = 4;

    private final double w;
    private final double h;

    public RectBodyShape(double w, double h) {
        this.w = w;
        this.h = h;
    }

    public double getBoundingWidth() {
        return w;
    }

    public double getBoundingHeight() {
        return h;
    }

    @Override
    public Polygon getShape(double x, double y) {
        final GeometricShapeFactory gsf = new GeometricShapeFactory();
        gsf.setNumPoints(VERTICES);
        gsf.setWidth(w);
        gsf.setHeight(h);
        gsf.setBase(new Coordinate(x, y));
        return gsf.createRectangle();
    }
}
