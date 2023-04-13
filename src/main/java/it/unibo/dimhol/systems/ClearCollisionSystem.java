package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.CollisionComponent;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.entity.Entity;

/**
 * A system to remove all collision components before next update cycle.
 */
public class ClearCollisionSystem extends AbstractSystem {

    public ClearCollisionSystem(World w) {
        super(w, CollisionComponent.class);
    }

    @Override
    public void process(Entity e, long dt) {
        var cc = (CollisionComponent) e.getComponent(CollisionComponent.class);
        e.removeComponent(cc);
    }
}
