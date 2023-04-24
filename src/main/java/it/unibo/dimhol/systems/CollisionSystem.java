package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.BodyComponent;
import it.unibo.dimhol.components.CollisionComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;

/**
 * A system to register collisions between entities.
 */
public class CollisionSystem extends AbstractSystem {

    /**
     * Constructs a CollisionSystem. Iterates through entities with {@link BodyComponent}.
     *
     * @param w the world
     */
    public CollisionSystem(final World w) {
        super(w, BodyComponent.class);
    }

    /**
     * Register all the collisions by storing data in a {@link CollisionComponent}
     * so that next system can handle them.
     *
     * @param e the entity to process
     * @param dt the delta time
     */
    @Override
    public void process(final Entity e, final long dt) {
        for (var o : this.world.getEntities()) {
            if (!o.equals(e) && o.hasComponent(BodyComponent.class)) {
                this.checkCollision(e, o);
            }
        }
    }

    private void checkCollision(final Entity e, final Entity o) {
        var p1 = (PositionComponent) e.getComponent(PositionComponent.class);
        var b1 = (BodyComponent) e.getComponent(BodyComponent.class);
        var p2 = (PositionComponent) o.getComponent(PositionComponent.class);
        var b2 = (BodyComponent) o.getComponent(BodyComponent.class);
        if (this.collisionHappens(p1,p2,b1,b2)) {
            this.registerCollision(e, o);
        }
    }

    private void registerCollision(final Entity e, final Entity o) {
        if (!e.hasComponent(CollisionComponent.class)) {
            e.addComponent(new CollisionComponent());
        }
        var cc = (CollisionComponent) e.getComponent(CollisionComponent.class);
        cc.addCollided(o);
    }

    private boolean collisionHappens(PositionComponent p1, PositionComponent p2,
                                     BodyComponent b1, BodyComponent b2) {
                return b1.getBodyShape().getShape(p1.getPos().getX(),p1.getPos().getY())
                        .intersects(b2.getBodyShape().getShape(p2.getPos().getX(),p2.getPos().getY()));
    }
}
