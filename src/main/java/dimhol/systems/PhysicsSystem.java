package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.components.BodyComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.PositionComponent;

/**
 * A system to handle physical collision.
 */
public class PhysicsSystem extends AbstractSystem {

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
     * @param entity  the entity to process
     * @param deltaTime the delta time
     */
    @Override
    protected void process(final Entity entity, final double deltaTime, final World world) {
        final var cc = (CollisionComponent) entity.getComponent(CollisionComponent.class);
        final var b1 = (BodyComponent) entity.getComponent(BodyComponent.class);
        final var p1 = (PositionComponent) entity.getComponent(PositionComponent.class);
        if (b1.isSolid() && cc.getCollided().stream()
                .map(collided -> (BodyComponent) collided.getComponent(BodyComponent.class))
                .anyMatch(BodyComponent::isSolid)) {
            p1.resetToLastPos();
        }
    }
}
