package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.CollisionComponent;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.entity.Entity;

/**
 * A system to remove all collision components before next update cycle.
 */
public class ClearCollisionSystem extends AbstractSystem {

    /**
     * Constructs a ClearCollisionSystem. Iterates through {@link CollisionComponent}.
     *
     * @param w the world
     */
    public ClearCollisionSystem(World w) {
        super(w, CollisionComponent.class);
    }

    /**
     * Removes CollisionComponent from the entity.
     *
     * @param e the entity to process
     * @param dt the delta time
     */
    @Override
    public void process(final Entity e, final long dt) {
        var cc = (CollisionComponent) e.getComponent(CollisionComponent.class);
        e.removeComponent(cc);
    }
}
