package it.unibo.dimhol.components;

public class CoinPocketComponent implements PocketComponent{
    private int coinCurrentAmount;

    public CoinPocketComponent(){
        this.coinCurrentAmount = 0;
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
