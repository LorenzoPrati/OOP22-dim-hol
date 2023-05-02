package it.unibo.dimhol.systems;

import it.unibo.dimhol.core.WorldImpl;
import it.unibo.dimhol.components.CollisionComponent;
import it.unibo.dimhol.components.PickableComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.RemoveEntityEvent;

public class ItemSystem extends AbstractSystem{

    public ItemSystem(WorldImpl w) {
        super(w, PickableComponent.class, CollisionComponent.class);
    }

    @Override
    public void process(Entity e, double dt) {
       var collided = (CollisionComponent)e.getComponent(CollisionComponent.class);
       var pickable = (PickableComponent)e.getComponent(PickableComponent.class);
       var effect = pickable.getEffect();
       for (var c : collided.getCollided()) {
           if(effect.canUseOn(c)){
               System.out.println("Heart picked");
               effect.applyOn(c);
               this.world.notifyEvent(new RemoveEntityEvent(e));
           }
       }
    }
}
