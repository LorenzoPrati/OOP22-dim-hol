package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.BodyComponent;
import it.unibo.dimhol.components.CollisionComponent;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;

import java.lang.reflect.GenericArrayType;

/**
 * A system to register all collisions between entities.
 * A {@link it.unibo.dimhol.components.CollisionComponent} is attached to make sure next systems can handle them.
 */
public class CollisionSystem extends AbstractSystem {
    public CollisionSystem(World w) {
        super(w, BodyComponent.class);
    }

    @Override
    public void process(Entity e, long dt) {
        var p1 = (PositionComponent) e.getComponent(PositionComponent.class);
        var b1 = (BodyComponent) e.getComponent(BodyComponent.class);
        for (var other : this.world.getEntities()) {
            if (!other.equals(e) && other.hasComponent(BodyComponent.class)) {
                var p2 = (PositionComponent) other.getComponent(PositionComponent.class);
                var b2 = (BodyComponent) other.getComponent(BodyComponent.class);
                if (this.collisionHappens(p1,p2,b1,b2)) {
                    p1.resetToLastPos();
                    p2.resetToLastPos();
                    if (!e.hasComponent(CollisionComponent.class)) {
                        e.addComponent(new CollisionComponent(other));
                    }
                }
            }
        }
    }

    private boolean collisionHappens(PositionComponent p1, PositionComponent p2, BodyComponent b1, BodyComponent b2) {
        return b1.getBs().getShape(p1.getPos().getX(),p1.getPos().getY())
                .intersects(b2.getBs().getShape(p2.getPos().getX(),p2.getPos().getY()));
    }
}
