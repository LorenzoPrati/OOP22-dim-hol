package it.unibo.dimhol.components;

import org.locationtech.jts.math.Vector2D;

/**
 * Holds data about movement like speed of the entity and direction.
 */
public class MovementComponent implements Component {

    private Vector2D dir;
    private double speed;

    private boolean enabled;

    public MovementComponent(final Vector2D dir, final double speed, final boolean enabled) {
        this.dir = dir;
        this.speed = speed;
        this.enabled = enabled;
    }

    public Vector2D getDir() {
        return dir;
    }

    public void setDir(final Vector2D dir) {
        this.dir = dir;
    }

    public double getSpeed() {
        return speed;
    }

    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
