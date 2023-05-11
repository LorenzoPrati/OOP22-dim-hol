package dimhol.logic.effects;

import dimhol.components.AiComponent;
import dimhol.components.HealthComponent;
import dimhol.components.PlayerComponent;
import dimhol.entity.Entity;

public class HealthEffectsFactoryImpl implements HealthEffectFactory {
   
    private Effect createHealthEffect(final int amount,final boolean increase, 
        final boolean isPowerUp, final boolean toUseOnPlayer){
        return new Effect(){
            @Override
            public boolean canUseOn(Entity entity) {
                return toUseOnPlayer? entity.hasComponent(PlayerComponent.class) : 
                    entity.hasComponent(AiComponent.class);
            }

            @Override
            public void applyOn(Entity entity) {
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
            }
        };
    }

    @Override
    public Effect IncreasePlayerCurrentHealthEffect(final int amount){
        return createHealthEffect(amount, true, false, true);
    }

    @Override
    public Effect IncreasePlayerMaxHealthEffect(final int amount){
        return createHealthEffect(amount, true, true, true);
    }

    @Override
    public Effect DecreasePlayerCurrentHealthEffect(final int amout){
        return createHealthEffect(amout, false, false, true);
    }
     
    @Override
    public Effect DecreaseEnemyCurrentHealthEffect(final int amout){
        return createHealthEffect(amout, false, false, false);
    } 
}
