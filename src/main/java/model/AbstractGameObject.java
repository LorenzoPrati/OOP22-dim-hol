package model;

import model.common.P2d;
import model.common.State;
import model.common.Type;
import model.physics.CollisionBox;

/**
 * An abstract class to model a generic game object and implementing methods common to 
 * all game objects
 */
public abstract class AbstractGameObject implements GameObject {

    private P2d position;
    private int id;
    private Type type;
    private State state;
    private CollisionBox collisionBox;

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
    public Type getType() {
        return this.type;
    }

    @Override
    public void setType(Type t) {
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

}
