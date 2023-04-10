package it.unibo.dimhol.components;

import java.util.Optional;

/**
 * Holds data about an entity position.
 */
public class PositionComponent implements Component {

    private double x;
    private double y;
    private Optional<Double> lastX = Optional.empty();
    private Optional<Double> lastY = Optional.empty();

    public PositionComponent(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getLastX() {
        return lastX.get();
    }

    public void setLastX(final double lastX) {
        this.lastX = Optional.of(lastX);
    }

    public double getLastY() {
        return lastY.get();
    }

    public void setLastY(final double lastY) {
        this.lastY = Optional.of(lastY);
    }

    public void updateLastPos() {
        this.lastX = Optional.of(this.x);
        this.lastY = Optional.of(this.y);
    }

    public void resetToLastPos() {
        if (this.lastX.isPresent() && this.lastY.isPresent()) {
            this.x = this.lastX.get();
            this.y = this.lastY.get();
        }
    }

}
