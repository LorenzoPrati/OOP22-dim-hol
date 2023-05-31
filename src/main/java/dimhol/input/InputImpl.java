package dimhol.input;

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
    public boolean isUp() {
        return this.up;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isDown() {
        return this.down;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isLeft() {
        return this.left;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isRight() {
        return this.right;
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
    public boolean isAttacking() {
        return this.normalMelee;
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
    public void setAttacking(final boolean normalMelee) {
        this.normalMelee = normalMelee;
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

