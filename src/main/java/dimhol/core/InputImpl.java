package dimhol.core;

import org.locationtech.jts.math.Vector2D;

import java.util.Optional;

/**
 * Stores user input.
 */
public class InputImpl implements Input {

    private boolean up, down, left, right;
    private boolean normalMeele;
    private boolean shoot;
    private boolean specialMeele;
    private boolean chargeFireball;
    private boolean interact;

    public InputImpl() {
    }

    @Override
    public boolean isInteracting() {
        return this.interact;
    }

    @Override
    public boolean isMoving() {
        return this.up || this.down || this.left || this.right;
    }

    @Override
    public boolean isMeele() {
        return this.normalMeele || this.specialMeele;
    }

    @Override
    public boolean isShooting() {
        return this.shoot;
    }

    @Override
    public boolean isNormalMeele() {
        return this.normalMeele;
    }

    @Override
    public boolean isSpecialMeele() {
        return this.specialMeele;
    }

    @Override
    public boolean isChargingFireball() {
        return this.chargeFireball;
    }

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

    @Override
    public void setUp(final boolean up) {
        this.up = up;
    }

    @Override
    public void setDown(final boolean down) {
        this.down = down;
    }

    @Override
    public void setLeft(final boolean left) {
        this.left = left;
    }

    @Override
    public void setRight(final boolean right) {
        this.right = right;
    }

    @Override
    public void setShoot(final boolean shoot) {
        this.shoot = shoot;
    }

    @Override
    public void setNormalMeele(final boolean normalMeele) {
        this.normalMeele = normalMeele;
    }

    @Override
    public void setSpecialMeele(final boolean specialMeele) {
        this.specialMeele = specialMeele;
    }

    @Override
    public void setInteract(final boolean interact) {
        this.interact = interact;
    }

    @Override
    public void setChargeFireball(final boolean chargeFireball) {
        this.chargeFireball = chargeFireball;
    }
}
