package dimhol.logic.effects;

import dimhol.components.*;
import dimhol.entity.Entity;

public class CoinPocketEffectFactoryImpl implements CoinPocketEffectFactory{
    
    private Effect createCoinPocketEffect(final Class<? extends Component> componentToCheck,
    final int amount, final boolean increase){
        return new Effect(){
            @Override
            public boolean canUseOn(Entity entity) {
                return entity.hasComponent(componentToCheck);
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

    @Override
    public Effect increaseCoinPocketEffect(final int amount){
        return createCoinPocketEffect(PlayerComponent.class, amount, true);
    }

    @Override
    public Effect decreaseCoinPocketEffect(final int amount){
        return createCoinPocketEffect(PlayerComponent.class, amount, false);
    }
}
