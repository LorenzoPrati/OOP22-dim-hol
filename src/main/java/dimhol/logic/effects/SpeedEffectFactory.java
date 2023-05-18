package dimhol.logic.effects;

/**
 * An interface for a factory that creates effects about the speed.
 * @param amount
 * @return
 */
public interface SpeedEffectFactory {
    /**
     * A method to create an effect to increase speed.
     * @param amount to add.
     * @return
     */
    Effect increasePlayerSpeedEffect(final double amount);
}
