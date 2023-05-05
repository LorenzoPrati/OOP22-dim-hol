package dimhol.logic.effects;

import dimhol.components.CoinPocketComponent;
import dimhol.entity.Entity;

public class IncreaseCoinAmountEffect implements Effect{
    private final int n;

    public IncreaseCoinAmountEffect(final int n){
        this.n = n;
    }

    @Override
    public boolean canUseOn(Entity entity) {
       return entity.hasComponent(CoinPocketComponent.class);
    }

    @Override
    public void applyOn(Entity entity) {
        var coinPocket = (CoinPocketComponent)entity.getComponent(CoinPocketComponent.class);
        coinPocket.setAmount(coinPocket.getCurrentAmount() + this.n);
    }
    
}
