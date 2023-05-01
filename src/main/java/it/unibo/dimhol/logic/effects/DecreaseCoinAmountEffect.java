package it.unibo.dimhol.logic.effects;

import it.unibo.dimhol.components.CoinPocketComponent;
import it.unibo.dimhol.entity.Entity;

public class DecreaseCoinAmountEffect implements Effect{
    private final int price;

    public DecreaseCoinAmountEffect(final int price){
        this.price = price;
    }

    @Override
    public boolean canUseOn(Entity entity) {
       return entity.hasComponent(CoinPocketComponent.class);
    }

    @Override
    public void applyOn(Entity entity) {
        var coinPocket = (CoinPocketComponent)entity.getComponent(CoinPocketComponent.class);
        coinPocket.setAmount(coinPocket.getCurrentAmount() - this.price);
    }
    
}
