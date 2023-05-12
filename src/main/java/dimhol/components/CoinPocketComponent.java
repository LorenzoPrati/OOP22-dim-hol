package dimhol.components;

public class CoinPocketComponent implements Component{
    private int coinCurrentAmount;

    public CoinPocketComponent(int coins){
        this.coinCurrentAmount = coins;
    }

    public int getCurrentAmount() {
        return this.coinCurrentAmount;
    }

    public void setAmount(int newAmount) {
        this.coinCurrentAmount = newAmount;
    }
    
}
