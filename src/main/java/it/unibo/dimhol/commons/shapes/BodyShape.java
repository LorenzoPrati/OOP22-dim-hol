package it.unibo.dimhol.commons.shapes;

import org.locationtech.jts.geom.Polygon;

public interface BodyShape {
    Polygon getShape(double x, double y);

    double getBoundingWidth();

    double getBoundingHeight();

}
