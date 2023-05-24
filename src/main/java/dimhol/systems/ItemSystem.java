package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;
import dimhol.components.ItemComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.PlayerComponent;

public class ItemSystem extends AbstractSystem{

    public ItemSystem() {
        super(ItemComponent.class, CollisionComponent.class);
    }

    @Override
    public void process(final Entity entity, final double dt, final World world) {
        var collisionComp = (CollisionComponent) entity.getComponent(CollisionComponent.class);
        var itemComp = (ItemComponent) entity.getComponent(ItemComponent.class);
        for(var c: collisionComp.getCollided()){
            itemComp.applyEffect(entity, PlayerComponent.class);
            world.notifyEvent(new RemoveEntityEvent(c));
        }
    }
}
