package dimhol.logic.effects;

import java.util.Optional;

import dimhol.components.AiComponent;
import dimhol.components.Component;
import dimhol.components.HealthComponent;
import dimhol.components.PlayerComponent;
import dimhol.entity.Entity;
import dimhol.events.WorldEvent;

public class HealthEffectsFactoryImpl implements HealthEffectFactory {
   
    private Effect createHealthEffect(final Class<? extends Component> componentToCheck,final int amount,final boolean increase, 
        final boolean isPowerUp){
        return new Effect(){
            @Override
            public boolean canUseOn(Entity entity) {
                return entity.hasComponent(componentToCheck);
            }
            @Override
            public Optional<WorldEvent> applyOn(Entity entity) {
                var health = (HealthComponent)entity.getComponent(HealthComponent.class);
                if(!isPowerUp){
                    if(increase){
                        health.setCurrentHealth(health.getCurrentHealth() + amount);
                    }
                    else{
                        health.setCurrentHealth(health.getCurrentHealth() - amount);
                    }
                }
                else{
                    health.setMaxHealth(health.getMaxHealth() + amount);
                }
                return Optional.empty();
            }
        };
    }

    @Override
    public Effect increasePlayerCurrentHealthEffect(final int amount){
        return createHealthEffect(PlayerComponent.class,amount, true, false);
    }

    @Override
    public Effect increasePlayerMaxHealthEffect(final int amount){
        return createHealthEffect(PlayerComponent.class,amount, true, true);
    }

    @Override
    public Effect decreasePlayerCurrentHealthEffect(final int amount){
        return createHealthEffect(PlayerComponent.class,amount, false, false);
    }
     
    @Override
    public Effect decreaseEnemyCurrentHealthEffect(final int amount){
        return createHealthEffect(AiComponent.class,amount, false, false);
    } 
}
