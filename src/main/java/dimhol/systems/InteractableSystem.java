package dimhol.systems;

import dimhol.components.CollisionComponent;
import dimhol.components.InteractableComponent;
import dimhol.components.InteractorComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;

/**
 * A system wich applies the interactable objects's effects to the entities that collided with them, when it's possible.
 */
public final class InteractableSystem extends AbstractSystem {

    /**
     * Constructs a system wich operates on all the enities wich have Interactable and Collision components.
     */
    public InteractableSystem() {
        super(InteractableComponent.class, CollisionComponent.class); 
    }

    @Override
    public void process(final Entity entity, final double dt, final World world) {
        var collisionComp = (CollisionComponent) entity.getComponent(CollisionComponent.class);
        var interactableComp = (InteractableComponent) entity.getComponent(InteractableComponent.class);
        for (var c: collisionComp.getCollided()) {
            if (c.hasComponent(InteractorComponent.class)) { 
                var interactComp = (InteractorComponent) c.getComponent(InteractorComponent.class);
                if (interactComp.isInteracting()) {
                    var effectApplied = interactableComp.applyEffect(c, world);
                    if (effectApplied) {
                        world.notifyEvent(new RemoveEntityEvent(entity));
                    }
                }
            }
        }
    } 
}
