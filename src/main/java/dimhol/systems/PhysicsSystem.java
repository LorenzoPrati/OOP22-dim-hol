package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.components.BodyComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.PositionComponent;

public class PhysicsSystem extends AbstractSystem{

    /**
     * Constructs a PhysicSystem. Iterates through entities with {@link BodyComponent}
     * and {@link CollisionComponent}.
     *
     * @param w the world
     */
    public PhysicsSystem(WorldImpl w) {
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
