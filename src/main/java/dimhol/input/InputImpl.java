package dimhol.input;

/**
 * Implementation of Input interface.
 */
@SuppressWarnings("PMD.JUnit4TestShouldUseBeforeAnnotation") // setUp method name should be avoided
public class InputImpl implements Input {

    private boolean up;
    private boolean down;
    private boolean left;
    private boolean right;
    private boolean attacking;
    private boolean shooting;
    private boolean chargingFireball;
    private boolean interacting;

    /**
     * {@inheritDoc}
     */
    @Override
    public InputImpl clone() {
        try {
            return (InputImpl) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new UnsupportedOperationException(e);
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
        return this.interacting;
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
        return this.shooting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isAttacking() {
        return this.attacking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean isChargingFireball() {
        return this.chargingFireball;
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
    public void setShooting(final boolean shooting) {
        this.shooting = shooting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setAttacking(final boolean attacking) {
        this.attacking = attacking;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setInteracting(final boolean interacting) {
        this.interacting = interacting;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void setChargingFireball(final boolean chargingFireball) {
        this.chargingFireball = chargingFireball;
    }
}

