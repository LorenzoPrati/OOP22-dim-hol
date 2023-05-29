package dimhol.core;

import org.locationtech.jts.math.Vector2D;

import java.util.Optional;

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
    boolean isNormalMelee();

    /**
     * Checks if special melee.
     *
     * @return true if special melee
     */
    boolean isSpecialMelee();

    /**
     * Checks if charging fireball.
     *
     * @return true if charging fireball
     */
    boolean isChargingFireball();

    /**
     * Gets the current direction based on the input.
     *
     * @return the current direction
     */
    Optional<Vector2D> getDirection();

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
    void setNormalMeele(boolean normalMeele);

    /**
     * Sets the special melee attack.
     *
     * @param specialMeele
     */
    void setSpecialMeele(boolean specialMeele);

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
