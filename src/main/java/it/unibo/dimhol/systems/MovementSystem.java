package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.components.PositionComponent;
import it.unibo.dimhol.entity.Entity;
import org.locationtech.jts.math.Vector2D;

/**
 * A system to handle movement of entities.
 */
public class MovementSystem extends AbstractSystem {

    /**
     * Constructs a MovementSystem. Iterates through entities with {@link MovementComponent}.
     *
     * @param w the world
     */
    public MovementSystem(World w) {
        super(w, MovementComponent.class);
    }

    /**
     * Moves the entity based on its direction, speed and according to delta time.
     *
     * @param e the entity to process
     * @param dt the delta time
     */
    @Override
    public void process(Entity e, long dt) {
        var pos = (PositionComponent) e.getComponent(PositionComponent.class);
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        pos.updateLastPos();
        if (mov.isEnabled()) {
            pos.setPos(pos.getPos().add(mov.getDir().multiply(mov.getSpeed() + dt * 0.001)));
        }
    }
}
