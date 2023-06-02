package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.components.BodyComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.PositionComponent;

public class PhysicsSystem extends AbstractSystem{

    /**
     * Constructs a PhysicSystem. Iterates through entities with {@link BodyComponent}
     * and {@link CollisionComponent}.
     */
    public PhysicsSystem() {
        super(BodyComponent.class, CollisionComponent.class);
    }

    /**
     * Handle physical collision for the entity.
     *
     * @param e  the entity to process
     * @param dt
     */
    @Override
    protected void process(final Entity e, final double dt, final World world) {
        var cc = (CollisionComponent) e.getComponent(CollisionComponent.class);
        var b1 = (BodyComponent) e.getComponent(BodyComponent.class);
        var p1 = (PositionComponent) e.getComponent(PositionComponent.class);
        for (var collided : cc.getCollided()) {
            var b2 = (BodyComponent) collided.getComponent(BodyComponent.class);
            if (b1.isSolid() && b2.isSolid()) {
                p1.resetToLastPos();
                break;
            }
        }
    }
}
