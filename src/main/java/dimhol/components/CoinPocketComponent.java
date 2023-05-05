package dimhol.components;

public class CoinPocketComponent implements PocketComponent{
    private int coinCurrentAmount;

    public CoinPocketComponent(int coins){
        this.coinCurrentAmount = coins;
    }

    @Override
    public int getCurrentAmount() {
        return this.coinCurrentAmount;
    }

    @Override
    public void setAmount(int newAmount) {
        this.coinCurrentAmount = newAmount;
    }
    
}
