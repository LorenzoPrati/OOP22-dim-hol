package dimhol.core;

import org.locationtech.jts.math.Vector2D;

import javax.management.OperationsException;
import java.util.Optional;

/**
 * Stores user input.
 */
public class InputImpl implements Input {

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean normalMelee;
    private boolean shoot;
    private boolean specialMelee;
    private boolean chargeFireball;
    private boolean interact;

    /**
     * {@inheritDoc}
     */
    @Override
    public InputImpl clone() {
        try {
            return (InputImpl) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e.getMessage());
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isInteracting() {
        return this.interact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isMoving() {
        return this.up || this.down || this.left || this.right;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isShooting() {
        return this.shoot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isNormalMelee() {
        return this.normalMelee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isSpecialMelee() {
        return this.specialMelee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isChargingFireball() {
        return this.chargeFireball;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Vector2D> getDirection() {
        if (this.up) {
            return Optional.of(new Vector2D(0, -1));
        }
        if (this.down) {
            return Optional.of(new Vector2D(0, 1));
        }
        if (this.left) {
            return Optional.of(new Vector2D(-1, 0));
        }
        if (this.right) {
            return Optional.of(new Vector2D(1, 0));
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setUp(final boolean up) {
        this.up = up;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setDown(final boolean down) {
        this.down = down;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setLeft(final boolean left) {
        this.left = left;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setRight(final boolean right) {
        this.right = right;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setShoot(final boolean shoot) {
        this.shoot = shoot;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setNormalMeele(final boolean normalMelee) {
        this.normalMelee = normalMelee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setSpecialMeele(final boolean specialMelee) {
        this.specialMelee = specialMelee;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInteract(final boolean interact) {
        this.interact = interact;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setChargeFireball(final boolean chargeFireball) {
        this.chargeFireball = chargeFireball;
    }
}

