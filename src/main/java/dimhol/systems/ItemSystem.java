package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;
import dimhol.components.CollisionComponent;
import dimhol.components.PickableComponent;

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
