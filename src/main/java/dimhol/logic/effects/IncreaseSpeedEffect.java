package dimhol.logic.effects;

import dimhol.components.MovementComponent;
import dimhol.entity.Entity;

public class IncreaseSpeedEffect implements Effect {
    private final double n;

    public IncreaseSpeedEffect(final double n){
        this.n = n;
    }

    @Override
    public boolean canUseOn(Entity entity) {
        return entity.hasComponent(MovementComponent.class);
    }

    @Override
    public void applyOn(Entity entity) {
        var moveComp = (MovementComponent) entity.getComponent(MovementComponent.class);
        moveComp.setSpeed(moveComp.getSpeed() + this.n);
    }
    
}
