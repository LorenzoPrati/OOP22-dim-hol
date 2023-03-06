package model.dynamic;

import model.AbstractGameObject;
import model.common.Direction;
import model.common.P2d;

/**
 * A class to model a generic dynamic game object
 */
public abstract class AbstractDynamicObject extends AbstractGameObject {

    private Direction direction;
    private double speed;
    private boolean moving;
    private P2d lastPosition;

    public abstract void update(long dt);

    public void moveBasedOnDirection(long dt) {
        this.lastPosition = this.getPosition();
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

    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(double speed) {
        this.speed = speed;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public boolean isCollidingWithWalls() {
        return false;
    }

    public P2d getLastPosition() {
        return lastPosition;
    }

    public void setLastPosition(P2d lastPosition) {
        this.lastPosition = lastPosition;
    }

    public void bounceBack() {
        this.setPosition(this.getLastPosition());
        this.getCollisionBox().translate(this.getPosition());
    }

}
