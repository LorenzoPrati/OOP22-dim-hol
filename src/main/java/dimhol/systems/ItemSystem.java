package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;
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
        if(interactableComp.isPickable()){
            for(var c: collisionComp.getCollided()){
                if(effect.canUseOn(c)){
                    effect.applyOn(c);
                    this.world.notifyEvent(new RemoveEntityEvent(e));
                }
            }
        }
        else{
            for(var c: collisionComp.getCollided()){
                if(c.hasComponent(InteractorComponent.class)){
                    var interactorComp =  (InteractorComponent) c.getComponent(InteractorComponent.class);
                    if(interactorComp.isInteracting()){
                        if(effect.canUseOn(c)){
                            effect.applyOn(c);
                        }
                    }
                }
            }
        }
    }
}
