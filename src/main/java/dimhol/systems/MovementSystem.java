package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;

/**
 * A system to handle movement of entities.
 */
public class MovementSystem extends AbstractSystem {

    /**
     * Constructs a MovementSystem. Iterates through entities with {@link MovementComponent}.
     *
     * @param w the world
     */
    public MovementSystem(WorldImpl w) {
        super(w, MovementComponent.class);
    }

    /**
     * Moves the entity based on its direction, speed and according to delta time.
     *
     * @param e  the entity to process
     * @param dt
     */
    @Override
    public void process(Entity e, double dt) {
        var pos = (PositionComponent) e.getComponent(PositionComponent.class);
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        pos.updateLastPos();
        if (mov.isEnabled()) {
            pos.setPos(pos.getPos().add(mov.getDir().multiply(mov.getSpeed() * dt)));
        }
    }
}