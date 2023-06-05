package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.components.BodyComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.PositionComponent;

/**
 * A system to register collisions between entities.
 */
public class CollisionSystem extends AbstractSystem {

    /**
     * Constructs a CollisionSystem. Iterates through entities with {@link BodyComponent}.
     */
    public CollisionSystem() {
        super(BodyComponent.class);
    }

    /**
     * Register all the collisions by storing data in a {@link CollisionComponent}
     * so that next system can handle them.
     *
     * @param entity the entity to process
     * @param deltaTime the delta time
     */
    @Override
    protected void process(final Entity entity, final double deltaTime, final World world) {
        world.getEntities().stream()
                .filter(other -> !other.equals(entity) && other.hasComponent(BodyComponent.class))
                .forEach(other -> this.checkCollision(entity, other));
    }

    private void checkCollision(final Entity entity, final Entity other) {
        if (this.collisionHappens((PositionComponent) entity.getComponent(PositionComponent.class),
                                    (PositionComponent) other.getComponent(PositionComponent.class),
                                    (BodyComponent) entity.getComponent(BodyComponent.class),
                                    (BodyComponent) other.getComponent(BodyComponent.class))) {
            this.registerCollision(entity, other);
        }
    }

    private void registerCollision(final Entity entity, final Entity other) {
        if (!entity.hasComponent(CollisionComponent.class)) {
            entity.addComponent(new CollisionComponent());
        }
        final var cc = (CollisionComponent) entity.getComponent(CollisionComponent.class);
        cc.addCollided(other);
    }

    private boolean collisionHappens(final PositionComponent p1, final PositionComponent p2,
                                     final BodyComponent b1, final BodyComponent b2) {
                return b1.getBodyShape().computeShape(p1.getPos())
                        .intersects(b2.getBodyShape().computeShape(p2.getPos()));
    }
}
