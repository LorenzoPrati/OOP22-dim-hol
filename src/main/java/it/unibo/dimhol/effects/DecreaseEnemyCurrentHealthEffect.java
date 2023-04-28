package it.unibo.dimhol.effects;

import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.HealthComponent;
import it.unibo.dimhol.entity.Entity;

public class DecreaseEnemyCurrentHealthEffect implements Effect{
    private final int n;

    public DecreaseEnemyCurrentHealthEffect(final int n){
        this.n = n;
    }

    @Override
    public boolean canUseOn(Entity entity) {
        return entity.hasComponent(AiComponent.class);
    }

    @Override
    public void applyOn(Entity entity) {
        var health = (HealthComponent)entity.getComponent(HealthComponent.class);
        health.setCurrentHealth(health.getCurrentHealth() + this.n);
    }
}
