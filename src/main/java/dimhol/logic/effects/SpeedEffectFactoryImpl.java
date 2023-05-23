package dimhol.logic.effects;

import java.util.Optional;

import dimhol.components.Component;
import dimhol.components.MovementComponent;
import dimhol.components.PlayerComponent;
import dimhol.entity.Entity;
import dimhol.events.WorldEvent;

public class SpeedEffectFactoryImpl implements SpeedEffectFactory {
    private Effect createSpeedEffect(final Class<? extends Component> componentToCheck,final double amount, 
    final boolean increase){
        return new Effect() {
            @Override
            public boolean canUseOn(Entity entity) {
                return entity.hasComponent(componentToCheck);
            }
            @Override
            public Optional<WorldEvent> applyOn(Entity entity) {
                var move = (MovementComponent)entity.getComponent(MovementComponent.class);
                if(increase){
                    move.setSpeed(move.getSpeed() + amount);
                }
                else{
                    move.setSpeed(move.getSpeed() - amount);
                } 
                return Optional.empty();
            }
        };
    }

    public Effect increasePlayerSpeedEffect(final double amount){
        return createSpeedEffect(PlayerComponent.class,amount, true);
    }
}
