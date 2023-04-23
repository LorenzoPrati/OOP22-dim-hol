package it.unibo.dimhol.components;

import org.locationtech.jts.math.Vector2D;

/**
 * Holds data about movement of an entity.
 */
public class MovementComponent implements Component {

    private Vector2D dir;
    private double speed;
    private boolean enabled;

    /**
     * Constructs a MovementComponent with given direction, speed and movement enabling flag.
     *
     * @param dir the direction
     * @param speed the speed
     * @param enabled the flag to enable movement. True to enable movement, false otherwise.
     */
    public MovementComponent(final Vector2D dir, final double speed, final boolean enabled) {
        this.dir = dir;
        this.speed = speed;
        this.enabled = enabled;
    }

    /**
     * Gets the direction.
     *
     * @return the vector representing the direction
     */
    public Vector2D getDir() {
        return this.dir;
    }

    /**
     * Sets the direction.
     *
     * @param dir the direction to set.
     */
    public void setDir(final Vector2D dir) {
        this.dir = dir;
    }

    /**
     * Gets the speed.
     *
     * @return the speed
     */
    public double getSpeed() {
        return this.speed;
    }

    /**
     * Sets the speed.
     *
     * @param speed the speed to set.
     */
    public void setSpeed(final double speed) {
        this.speed = speed;
    }

    /**
     * Checks if the movement is enabled.
     *
     * @return true if the movement is enabled, false otherwise.
     */
    public boolean isEnabled() {
        return this.enabled;
    }

    /**
     * Enable or disable the movement.
     *
     * @param enabled the flag to set to enable or disable movement. True to enable it, false otherwise.
     */
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
}
