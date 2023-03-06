package model;

import model.common.P2d;
import model.common.State;
import model.common.ObjectType;
import model.physics.CollisionBox;
import model.rooms.Room;

/**
 * An abstract class to model a generic game object and implementing methods common to 
 * all game objects
 */
public abstract class AbstractGameObject implements GameObject {

    private P2d position;
    private int id;
    private ObjectType type;
    private State state;
    private CollisionBox collisionBox;
    private Room room;

    /**
     * every game object has its update method
     */
    public abstract void update(long dt);

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
    }

    @Override
    public P2d getPosition() {
        return this.position;
    }

    @Override
    public void setPosition(P2d p) {
        this.position = p;
    }

    @Override
    public CollisionBox getCollisionBox() {
        return this.collisionBox;
    }

    @Override
    public void setCollisionBox(CollisionBox cb) {
        this.collisionBox = cb;
    }

    @Override
    public ObjectType getType() {
        return this.type;
    }

    @Override
    public void setType(ObjectType t) {
        this.type = t;
    }

    @Override
    public State getState() {
        return this.state;
    }

    @Override
    public void setState(State s) {
        this.state = s;
    }

    @Override
    public Room getRoom() {
        return this.room;
    }

}
