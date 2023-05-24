package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;
import dimhol.components.CoinPocketComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.InteractableComponent;
import dimhol.components.InteractorComponent;

public class ItemSystem extends AbstractSystem{

    public ItemSystem(WorldImpl w) {
        super(w, InteractableComponent.class, CollisionComponent.class);
    }

    @Override
    public void process(Entity e, double dt) {
        var collisionComp = (CollisionComponent) e.getComponent(CollisionComponent.class);
        var interactableComp = (InteractableComponent) e.getComponent(InteractableComponent.class);
        var effect = interactableComp.getEffect();

        for(var c: collisionComp.getCollided()){
            if(interactableComp.isPickable() && effect.canUseOn(c)){
                effect.applyOn(c);
                this.world.notifyEvent(new RemoveEntityEvent(c));
            }
            else{
                if(c.hasComponent(InteractorComponent.class)){
                    var interactComp = (InteractorComponent)c.getComponent(InteractorComponent.class);
                    if(interactComp.isInteracting() && effect.canUseOn(c)){
                        if(interactableComp.hasPriceToPay()){
                            if(c.hasComponent(CoinPocketComponent.class)){
                                var coinPocketComp = (CoinPocketComponent)c.getComponent(CoinPocketComponent.class);
                                if(interactableComp.getPrice().get()<=coinPocketComp.getCurrentAmount()){
                                    effect.applyOn(c);
                                }
                            }
                        }
                        else{
                            var eventToNotify = effect.applyOn(c);
                            this.world.notifyEvent(eventToNotify.get());
                        } 
                    }
                }
            }
        }
    }
}
