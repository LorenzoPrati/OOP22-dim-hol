package it.unibo.dimhol.components;

import org.locationtech.jts.math.Vector2D;

import java.util.Optional;

/**
 * Holds data about an entity position.
 */
public class PositionComponent implements Component {

    private Vector2D pos;
    private Optional<Vector2D> lastPos;

    public PositionComponent(final Vector2D pos) {
        this.pos = pos;
    }

    public Vector2D getPos() {
        return pos;
    }

    public void setPos(final Vector2D pos) {
        this.pos = pos;
    }

    public Optional<Vector2D> getLastPos() {
        return lastPos;
    }

    public void setLastPos(final Optional<Vector2D> lastPos) {
        this.lastPos = lastPos;
    }

    public void updateLastPos() {
        this.lastPos = Optional.of(this.pos);
    }

    public void resetToLastPos() {
        if (this.lastPos.isPresent()) {
            this.pos = this.lastPos.get();
        }
    }

}
