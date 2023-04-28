package it.unibo.dimhol.effects;

import it.unibo.dimhol.components.KeyPocketComponent;
import it.unibo.dimhol.entity.Entity;

public class IncreaseKeyAmountEffect implements Effect{
    private final int n;

    public IncreaseKeyAmountEffect(final int n){
        this.n = n;
    }

    @Override
    public boolean canUseOn(Entity entity) {
        return entity.hasComponent(KeyPocketComponent.class);
    }

    @Override
    public void applyOn(Entity entity) {
        var keyPocket = (KeyPocketComponent) entity.getComponent(KeyPocketComponent.class);
        keyPocket.setAmount(keyPocket.getCurrentAmount() + this.n);
    }
    
}
