package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.BodyComponent;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.entity.Entity;

/**
 * A system to register all collisions between entities.
 * A {@link it.unibo.dimhol.components.CollisionComponent} is attached to make sure next systems can handle them.
 */
public class CollisionSystem extends AbstractSystem {
    public CollisionSystem(World w, Class<? extends Component>... comps) {
        super(w, comps);
    }

    @Override
    public void process(Entity e, long dt) {
        var b1 = (BodyComponent) e.getComponent(BodyComponent.class);
    }
}
