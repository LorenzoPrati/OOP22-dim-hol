package it.unibo.dimhol.logic.effects;

import it.unibo.dimhol.components.*;
import it.unibo.dimhol.entity.Entity;

public class CoinPocketEffectFactoryImpl implements CoinPocketEffectFactory{
    
    private Effect createCoinPocketEffect(final int amount, final boolean increase){
        return new Effect(){
            @Override
            public boolean canUseOn(Entity entity) {
                return entity.hasComponent(PlayerComponent.class);
            }

            @Override
            public void applyOn(Entity entity) {
                var coinPocket = (CoinPocketComponent)entity.getComponent(CoinPocketComponent.class);
                if(increase){
                    coinPocket.setAmount(coinPocket.getCurrentAmount() + amount);
                }
                else{
                    coinPocket.setAmount(coinPocket.getCurrentAmount() - amount);
                }
            }
        };
    }

    public Effect IncreaseCoinPocketEffect(final int amount){
        return createCoinPocketEffect(amount, true);
    }

    public Effect DecreaseCoinPocketEffect(final int amount){
        return createCoinPocketEffect(amount, false);
    }
   
}
