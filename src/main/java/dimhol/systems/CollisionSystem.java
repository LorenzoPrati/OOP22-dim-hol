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
     * @param dt
     */
    @Override
    protected void process(final Entity entity, final double dt, final World world) {
        for (var other : world.getEntities()) {
            if (!other.equals(entity) && other.hasComponent(BodyComponent.class)) {
                this.checkCollision(entity, other);
            }
        }
    }

    private void checkCollision(final Entity entity, final Entity other) {
        var p1 = (PositionComponent) entity.getComponent(PositionComponent.class);
        var b1 = (BodyComponent) entity.getComponent(BodyComponent.class);
        var p2 = (PositionComponent) other.getComponent(PositionComponent.class);
        var b2 = (BodyComponent) other.getComponent(BodyComponent.class);
        if (this.collisionHappens(p1,p2,b1,b2)) {
            this.registerCollision(entity, other);
        }
    }

    private void registerCollision(final Entity entity, final Entity other) {
        if (!entity.hasComponent(CollisionComponent.class)) {
            entity.addComponent(new CollisionComponent());
        }
        var cc = (CollisionComponent) entity.getComponent(CollisionComponent.class);
        cc.addCollided(other);
    }

    private boolean collisionHappens(final PositionComponent p1, final PositionComponent p2,
                                     final BodyComponent b1, final BodyComponent b2) {
                return b1.getBodyShape().computeShape(p1.getPos())
                        .intersects(b2.getBodyShape().computeShape(p2.getPos()));
    }
}
