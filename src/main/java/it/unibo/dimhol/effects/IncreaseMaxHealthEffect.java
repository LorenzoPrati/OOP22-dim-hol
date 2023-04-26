package it.unibo.dimhol.effects;

import it.unibo.dimhol.components.HealthComponent;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;

public class IncreaseMaxHealthEffect implements Effect {
    private final int n;

    public IncreaseMaxHealthEffect(final int n){
        this.n = n;
    }

    @Override
    public boolean canUseOn(Entity entity) {
        return entity.hasComponent(PlayerComponent.class);
    }

    @Override
    public void applyOn(Entity entity) {
        var health = (HealthComponent)entity.getComponent(HealthComponent.class);
        health.setMaxHealth(health.getMaxHealth() + this.n);
    }
    
}