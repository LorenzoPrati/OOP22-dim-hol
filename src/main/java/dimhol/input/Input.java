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
     * @param up true to set up movement
     */
    void setUp(boolean up);

    /**
     * Sets the down movement.
     *
     * @param down true to set down movement
     */
    void setDown(boolean down);

    /**
     * Sets the left movement.
     *
     * @param left true to set left movement
     */
    void setLeft(boolean left);

    /**
     * Sets the right movement.
     *
     * @param right true to set right movement
     */
    void setRight(boolean right);

    /**
     * Sets the shooting.
     *
     * @param shooting true to set the shoot
     */
    void setShooting(boolean shooting);

    /**
     * Sets the sword attack.
     *
     * @param attacking true to set the sword attack
     */
    void setAttacking(boolean attacking);

    /**
     * Sets the interaction.
     *
     * @param interacting true to set the interaction
     */
    void setInteracting(boolean interacting);

    /**
     * Sets che fireball charging.
     *
     * @param chargingFireball true to set fireball
     */
    void setChargingFireball(boolean chargingFireball);
}
