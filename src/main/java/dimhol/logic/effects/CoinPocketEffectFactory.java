package dimhol.logic.effects;

/**
 * A factory that create effects about CoinPocketComponent.
 */
public interface CoinPocketEffectFactory {
    /**
     * A method to create an effect to increase the player's current amount of coins.
     * @param amount to add.
     * @return
     */
    Effect increaseCoinPocketEffect(final int amount);

    /**
     * A method to create an effect  to decrease the player's current amount of coins.
     * @param amount to subtract.
     * @return
     */
    Effect decreaseCoinPocketEffect(final int amount);
}
