package it.unibo.dimhol.logic.effects;

import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;

public class SpeedEffectFactoryImpl implements SpeedEffectFactory {
    
    private Effect createSpeedEffect(final double amount, final boolean increase){
        return new Effect() {

            @Override
            public boolean canUseOn(Entity entity) {
                return entity.hasComponent(PlayerComponent.class);
            }

            @Override
            public void applyOn(Entity entity) {
                var move = (MovementComponent)entity.getComponent(MovementComponent.class);
                if(increase){
                    move.setSpeed(move.getSpeed() + amount);
                }
                else{
                    move.setSpeed(move.getSpeed() - amount);
                } 
            }
        };
    }

    public Effect increaseSpeedEffect(final double amount){
        return createSpeedEffect(amount, true);
    }
}
