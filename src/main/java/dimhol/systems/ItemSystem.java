package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;
import dimhol.components.ItemComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.PlayerComponent;

/**
 * A system wich applies the item's effect to the entities wich collided with it, when it's possible.
 */
public final class ItemSystem extends AbstractSystem {

    /**
     * Constructs a system wich operates on all the entities wich have Item and Collision components.
     */
    public ItemSystem() {
        super(ItemComponent.class, CollisionComponent.class);
    }

    @Override
    public void process(final Entity entity, final double dt, final World world) {
        var collisionComp = (CollisionComponent) entity.getComponent(CollisionComponent.class);
        var itemComp = (ItemComponent) entity.getComponent(ItemComponent.class);
        for (var c: collisionComp.getCollided()) {
            var picked = itemComp.applyEffect(c, PlayerComponent.class);
            if (picked) {
                world.notifyEvent(new RemoveEntityEvent(entity));
            }
        }
    }
}
