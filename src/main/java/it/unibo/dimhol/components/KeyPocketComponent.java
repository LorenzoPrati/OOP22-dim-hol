package it.unibo.dimhol.components;

public class KeyPocketComponent implements PocketComponent {
    private int keyCurrentAmount;

    public KeyPocketComponent(){
        this.keyCurrentAmount = 0;
    }

    @Override
    public int getCurrentAmount() {
        return this.keyCurrentAmount;
    }

    @Override
    public void setAmount(int newAmount) {
        this.keyCurrentAmount = newAmount;
    }

}
