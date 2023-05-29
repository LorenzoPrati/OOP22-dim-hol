package dimhol.systems;

import dimhol.core.World;
import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.components.CollisionComponent;

/**
 * A system to remove all collision components before next update cycle.
 */
public class ClearCollisionSystem extends AbstractSystem {

    /**
     * Constructs a ClearCollisionSystem. Iterates through {@link CollisionComponent}.
     */
    public ClearCollisionSystem() {
        super(CollisionComponent.class);
    }

    /**
     * Removes CollisionComponent from the entity.
     *
     * @param entity  the entity to process
     * @param dt
     */
    @Override
    public void process(final Entity entity, final double dt, final World world) {
        var cc = (CollisionComponent) entity.getComponent(CollisionComponent.class);
        entity.removeComponent(cc);
    }
}
