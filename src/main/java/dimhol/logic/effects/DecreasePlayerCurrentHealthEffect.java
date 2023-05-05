package dimhol.logic.effects;

import dimhol.components.HealthComponent;
import dimhol.components.PlayerComponent;
import dimhol.entity.Entity;

public class DecreasePlayerCurrentHealthEffect implements Effect{
    private final int n;

    public DecreasePlayerCurrentHealthEffect(final int n){
        this.n = n;
    }

    @Override
    public boolean canUseOn(Entity entity) {
        return entity.hasComponent(PlayerComponent.class);
    }

    @Override
    public void applyOn(Entity entity) {
        var health = (HealthComponent)entity.getComponent(HealthComponent.class);
        health.setCurrentHealth(health.getCurrentHealth() - this.n);
    }
}
