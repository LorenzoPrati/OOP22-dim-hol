package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;
import dimhol.components.ItemComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.PlayerComponent;

public class ItemSystem extends AbstractSystem{

    public ItemSystem(WorldImpl w) {
        super(w, ItemComponent.class, CollisionComponent.class);
    }

    @Override
    public void process(Entity e, double dt) {
        var collisionComp = (CollisionComponent) e.getComponent(CollisionComponent.class);
        var itemComp = (ItemComponent) e.getComponent(ItemComponent.class);
        for(var c: collisionComp.getCollided()){
            itemComp.applyEffect(e, PlayerComponent.class);
            this.world.notifyEvent(new RemoveEntityEvent(c));
        }
    }
}
