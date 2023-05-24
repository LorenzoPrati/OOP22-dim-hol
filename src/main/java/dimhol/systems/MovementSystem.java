package dimhol.systems;

import dimhol.core.World;
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
     */
    public MovementSystem() {
        super(MovementComponent.class);
    }

    /**
     * Moves the entity based on its direction, speed and according to delta time.
     *
     * @param entity the entity to process
     * @param dt
     */
    @Override
    public void process(final Entity entity, final double dt, final World world) {
        var pos = (PositionComponent) entity.getComponent(PositionComponent.class);
        var mov = (MovementComponent) entity.getComponent(MovementComponent.class);
        pos.updateLastPos();
        if (mov.isEnabled()) {
            pos.setPos(pos.getPos().add(mov.getDir().multiply(mov.getSpeed() * dt)));
        }
    }
}
