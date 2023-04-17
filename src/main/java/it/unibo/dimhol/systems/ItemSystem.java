package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.CollisionComponent;
import it.unibo.dimhol.components.PickableComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.RemoveEntityEvent;

public class ItemSystem extends AbstractSystem{

    public ItemSystem(World w) {
        super(w, PickableComponent.class, CollisionComponent.class);
    }

    @Override
    public void process(Entity e, long dt) {
       var collided = (CollisionComponent)e.getComponent(CollisionComponent.class);
       var pickable = (PickableComponent)e.getComponent(PickableComponent.class);
       var effect = pickable.getEffect();
       if(effect.canUseOn(collided.getEntity())){
            System.out.println("Heart picked");
            effect.applyOn(collided.getEntity());
            this.world.notifyEvent(new RemoveEntityEvent(e));
       }
    }
}
