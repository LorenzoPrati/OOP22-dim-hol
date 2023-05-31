package dimhol.input;

/**
 * An interface to model input.
 */
public interface Input extends Cloneable {

    /**
     * Gets a copy of the input.
     *
     * @return a copy of the input
     */
    Input clone();

    /**
     * Checks if moving up.
     *
     * @return true if moving up
     */
    boolean isUp();

    /**
     * Checks if moving down.
     *
     * @return true if moving down
     */
    boolean isDown();

    /**
     * Checks if moving left.
     *
     * @return true if moving left
     */
    boolean isLeft();

    /**
     * Checks if moving right.
     *
     * @return true if moving right
     */
    boolean isRight();

    /**
     * Checks if interacting.
     *
     * @return true if interacting
     */
    boolean isInteracting();

    /**
     * Checks if moving.
     *
     * @return true if moving
     */
    boolean isMoving();

    /**
     * Checks if shooting.
     *
     * @return true if shooting
     */
    boolean isShooting();

    /**
     * Checks if normal melee.
     *
     * @return true if normal melee
     */
    boolean isAttacking();

    /**
     * Checks if charging fireball.
     *
     * @return true if charging fireball
     */
    boolean isChargingFireball();

    /**
     * Sets the up movement.
     *
     * @param up
     */
    void setUp(boolean up);

    /**
     * Sets the down movement.
     *
     * @param down
     */
    void setDown(boolean down);

    /**
     * Sets the left movement.
     *
     * @param left
     */
    void setLeft(boolean left);

    /**
     * Sets the right movement.
     *
     * @param right
     */
    void setRight(boolean right);

    /**
     * Sets the shooting.
     *
     * @param shoot
     */
    void setShoot(boolean shoot);

    /**
     * Sets the normal melee attack.
     *
     * @param normalMeele
     */
    void setAttacking(boolean normalMeele);

    /**
     * Sets the interaction.
     *
     * @param interact
     */
    void setInteract(boolean interact);

    /**
     * Sets che fireball charging.
     *
     * @param chargeFireball
     */
    void setChargeFireball(boolean chargeFireball);
}
