package it.unibo.dimhol.systems;

import it.unibo.dimhol.core.WorldImpl;
import it.unibo.dimhol.components.*;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.RemoveEntityEvent;

public class InteractionSystem extends AbstractSystem{

    /**
     * Constructs a system to operates on a given world and family of components.
     *
     * @param w     the world
     */
    public InteractionSystem(WorldImpl w) {
        super(w, InteractableComponent.class, CollisionComponent.class);
    }

    @Override
    public void process(Entity e, double dt) {
        var interactable = (InteractableComponent) e.getComponent(InteractableComponent.class);
        var cc = (CollisionComponent) e.getComponent(CollisionComponent.class);
        for (var collided : cc.getCollided()) {
            if (collided.hasComponent(InteractorComponent.class)) {
                var interactor = (InteractorComponent) collided.getComponent(InteractorComponent.class);
                /*
                todo if player display that interaction is possible
                 */
                if (interactor.isInteracting()) {
                    var eff = interactable.getEffect();
                    if (eff.canUseOn(collided)) {
                        eff.applyOn(collided);
                        this.world.notifyEvent(new RemoveEntityEvent(e));
                    }
                    interactor.setInteracting(false);
                }
            }
        }
    }
}
