package model.dynamic;

import org.locationtech.jts.geom.Coordinate;

import model.AbstractGameObject;
import model.common.Direction;
import model.common.ObjectType;
import model.common.State;
import model.physics.CollisionBox;
import model.rooms.Room;

/**
 * A class to model a generic dynamic game object.
 */
public abstract class AbstractDynamicObject extends AbstractGameObject implements DynamicObject {

    private Direction direction;
    private double speed;
    private boolean moving;
    private Coordinate lastPosition;

    public AbstractDynamicObject(Coordinate position, Integer id, ObjectType type, State state,
            CollisionBox collisionBox, Room room, int height, int width) {
        super(position, id, type, state, collisionBox, room, height, width);
        //TODO Auto-generated constructor stub
    }

    public abstract void update(long dt);

    /**
     * {@inheritDoc}
     */
    @Override
    public void move(long dt) {
        this.setLastPosition(this.getPosition());
        switch (this.getDirection()) {
            case UP:
                this.getPosition().setY(this.getPosition().getY() - (speed * dt * 0.001));
                break;
            case DOWN:
                this.getPosition().setY(this.getPosition().getY() + (speed * dt * 0.001));
                break;
            case LEFT:
                this.getPosition().setX(this.getPosition().getX() - (speed * dt * 0.001));
                break;
            case RIGHT:
                this.getPosition().setX(this.getPosition().getX() + (speed * dt * 0.001));
                break;
        }
        this.getCollisionBox().translate(this.getPosition());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Direction getDirection() {
        return this.direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public double getSpeed() {
        return speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpeed(double speed) {
        this.speed = speed;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMoving() {
        return moving;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void enableMovement() {
        this.moving = true;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isCollidingWithWalls() {
        //TODO: 
        return false;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Coordinate getLastPosition() {
        return this.lastPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLastPosition(Coordinate lastPosition) {
        this.lastPosition = lastPosition;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void bounceBack() {
        this.setPosition(this.getLastPosition());
        this.getCollisionBox().translate(this.getPosition());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resetMovement() {
        this.moving = false;
    }

}
