package model.physics;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import model.GameObject;

/**
 * Implements a rectangular collision box.
 */
public class CollisionBoxImpl implements CollisionBox {

    private int h;
    private int w;
    private Coordinate coordinate;
    private Geometry box;
    private GeometryFactory gf = new GeometryFactory();

    public CollisionBoxImpl(Coordinate c) {
        this.coordinate = c;
        this.box = gf.createPolygon(this.getCoordinates());
    }

    @Override
    public boolean intersects(CollisionBox cb) {
        return this.box.intersects(cb.getBox());
    }

    @Override
    public void translate(Coordinate c) {
        this.box = gf.createPolygon(this.getCoordinates());
    }

    @Override
    public Geometry getBox() {
        return this.box;
    }

    private Coordinate[] getCoordinates() {
        return new Coordinate[] {
                new Coordinate(coordinate.getX(), coordinate.getY()),
                new Coordinate(coordinate.getX(), coordinate.getY() + this.h),
                new Coordinate(coordinate.getX() + this.w, coordinate.getY()),
                new Coordinate(coordinate.getX() + this.w, coordinate.getY() + this.h)
        };
    }

}
