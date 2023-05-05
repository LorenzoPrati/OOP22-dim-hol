package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.components.CollisionComponent;

/**
 * A system to remove all collision components before next update cycle.
 */
public class ClearCollisionSystem extends AbstractSystem {

    /**
     * Constructs a ClearCollisionSystem. Iterates through {@link CollisionComponent}.
     *
     * @param w the world
     */
    public ClearCollisionSystem(WorldImpl w) {
        super(w, CollisionComponent.class);
    }

    /**
     * Removes CollisionComponent from the entity.
     *
     * @param e  the entity to process
     * @param dt
     */
    @Override
    public void process(final Entity e, double dt) {
        var cc = (CollisionComponent) e.getComponent(CollisionComponent.class);
        e.removeComponent(cc);
    }
}
