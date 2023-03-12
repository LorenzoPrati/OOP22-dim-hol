package model;

import org.locationtech.jts.geom.Coordinate;

import model.common.ObjectType;
import model.common.State;
import model.physics.CollisionBox;
import model.rooms.Room;

/**
 * Implements methods common to all game objects.
 */
public abstract class AbstractGameObject implements GameObject {

    private Coordinate position;
    private final Integer id;
    private final ObjectType type;
    private State state;
    private final CollisionBox collisionBox;
    private Room room;

    public AbstractGameObject(Coordinate position, Integer id, ObjectType type, State state, CollisionBox collisionBox,
            Room room) {
        this.position = position;
        this.id = id;
        this.type = type;
        this.state = state;
        this.collisionBox = collisionBox;
        this.room = room;
    }

    public abstract void update(long dt);

    /**
     * {@inheritDoc}
     */
    @Override
    public CollisionBox getCollisionBox() {
        return this.collisionBox;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Integer getId() {
        return this.id;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Coordinate getPosition() {
        return this.position;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Room getRoom() {
        return this.room;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public State getState() {
        return this.state;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ObjectType getType() {
        return this.type;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setPosition(Coordinate c) {
        this.position = c;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setState(State s) {
        this.state = s;
    }
}
