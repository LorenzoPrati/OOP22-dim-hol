package dimhol.logic.effects;

/**
 * A factory that creates all the effect about HealthComponent.
 */
public interface HealthEffectFactory {
    /**
     * A method to create an effect to increase the player's current health.
     * @param amount to add to the current health. 
     * @return
     */
    Effect increasePlayerCurrentHealthEffect(final int amount);

    /**
     * A method to create an effect to increase the player's max health.
     * @param amount to add to the max health.
     * @return
     */
    Effect increasePlayerMaxHealthEffect(final int amount);

    /**
     * A method to create an effect to decrease the player's current health.
     * @param amout to subtract to the current health.
     * @return
     */
    Effect decreasePlayerCurrentHealthEffect(final int amout);

    /**
     * A method to create an effect to decrease the enemy's current health.
     * @param amout to subtract to the current health.
     * @return
     */
    Effect decreaseEnemyCurrentHealthEffect(final int amout);
}
