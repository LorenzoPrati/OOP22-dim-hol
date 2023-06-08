package dimhol.components;

/**
 * A component to manage the player's coin amount.
 */
public class CoinPocketComponent implements Component {
    private int coinCurrentAmount;

    /**
     * Constructs a CoinPocketComponent.
     * @param coins the initial coin amount.
     */
    public CoinPocketComponent(final int coins) {
        this.coinCurrentAmount = coins;
    }

    /**
     * @return the current amount.
     */
    public int getCurrentAmount() {
        return this.coinCurrentAmount;
    }

    /**
     * A method to set the new amount.
     * @param newAmount
     */
    public void setAmount(final int newAmount) {
        this.coinCurrentAmount = newAmount;
    }
}
