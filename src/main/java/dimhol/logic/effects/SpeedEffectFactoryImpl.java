package dimhol.logic.effects;

import dimhol.components.Component;
import dimhol.components.MovementComponent;
import dimhol.components.PlayerComponent;
import dimhol.entity.Entity;

public class SpeedEffectFactoryImpl implements SpeedEffectFactory {
    private Effect createSpeedEffect(final Class<? extends Component> componentToCheck,final double amount, 
    final boolean increase){
        return new Effect() {
            @Override
            public boolean canUseOn(Entity entity) {
                return entity.hasComponent(componentToCheck);
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

    public Effect increasePlayerSpeedEffect(final double amount){
        return createSpeedEffect(PlayerComponent.class,amount, true);
    }
}
