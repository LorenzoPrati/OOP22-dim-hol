package dimhol.components;

import org.locationtech.jts.math.Vector2D;

import java.util.Optional;

/**
 * Holds data about an entity position.
 */
public class PositionComponent implements Component {

    private Vector2D pos;
    private Optional<Vector2D> lastPos = Optional.empty();
    private int z;

    /**
     * Constructs a PositionComponent with given position and z.
     *
     * @param pos the position
     * @param z
     */
    public PositionComponent(final Vector2D pos, int z) {
        this.pos = pos;
        this.z = z;
    }

    /**
     * Gets the position.
     *
     * @return the vector representing the position
     */
    public Vector2D getPos() {
        return this.pos;
    }

    /**
     * Sets the position to given value.
     *
     * @param pos the position to set.
     */
    public void setPos(final Vector2D pos) {
        this.pos = pos;
    }

    /**
     * Gets the last position.
     *
     * @return the last position
     */
    public Optional<Vector2D> getLastPos() {
        return this.lastPos;
    }

    /**
     * Sets the last position.
     *
     * @param lastPos the position to set
     */
    public void setLastPos(final Optional<Vector2D> lastPos) {
        this.lastPos = lastPos;
    }

    /**
     * Updates the last position by setting its value equals to current position.
     */
    public void updateLastPos() {
        this.lastPos = Optional.of(this.pos);
    }

    /**
     * Revert the current position to last position, if present.
     */
    public void resetToLastPos() {
        if (this.lastPos.isPresent()) {
            this.pos = this.lastPos.get();
        }
    }

    /**
     * Gets the z.
     *
     * @return the z
     */
    public int getZ() {
        return this.z;
    }

    /**
     * Sets the z.
     *
     * @param z the z to set
     */
    public void setZ(final int z) {
        this.z = z;
    }
}
