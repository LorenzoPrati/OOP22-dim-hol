package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.BodyComponent;
import it.unibo.dimhol.components.CollisionComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;

public class PhysicsSystem extends AbstractSystem{

    /**
     * Constructs a PhysicSystem. Iterates through entities with {@link BodyComponent}
     * and {@link CollisionComponent}.
     *
     * @param w the world
     */
    public PhysicsSystem(World w) {
        super(w, BodyComponent.class, CollisionComponent.class);
    }

    /**
     * Handle physical collision for the entity.
     *
     * @param e  the entity to process
     * @param dt
     */
    @Override
    public void process(Entity e, double dt) {
        var cc = (CollisionComponent) e.getComponent(CollisionComponent.class);
        var b1 = (BodyComponent) e.getComponent(BodyComponent.class);
        var p1 = (PositionComponent) e.getComponent(PositionComponent.class);
        for (var c : cc.getCollided()) {
            var b2 = (BodyComponent) c.getComponent(BodyComponent.class);
            if (b1.isSolid() && b2.isSolid()) {
                p1.resetToLastPos();
                break;
            }
        }
    }
}
