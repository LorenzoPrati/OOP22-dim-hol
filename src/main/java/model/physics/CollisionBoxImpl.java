package model.physics;

import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.Geometry;
import org.locationtech.jts.geom.GeometryFactory;

import model.GameObject;

/**
 * Implements a rectangular collision box.
 */
public class CollisionBoxImpl implements CollisionBox{

    private final GameObject owner;
    private Geometry box;
    private GeometryFactory gf = new GeometryFactory();

    public CollisionBoxImpl(GameObject owner) {
        this.owner = owner;
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
            new Coordinate(this.owner.getPosition().getX(), this.owner.getPosition().getY()),
            new Coordinate(this.owner.getPosition().getX(), this.owner.getPosition().getY()+this.owner.getHeight()),
            new Coordinate(this.owner.getPosition().getX() + this.owner.getWidth(), this.owner.getPosition().getY()),
            new Coordinate(this.owner.getPosition().getX() + this.owner.getWidth(), this.owner.getPosition().getY()+this.owner.getHeight()),
        };
    }
    
}
