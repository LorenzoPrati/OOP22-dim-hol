package dimhol.systems;

import dimhol.components.MovementComponent;
import dimhol.components.PositionComponent;
import dimhol.core.World;
import dimhol.entity.Entity;

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
     * @param deltaTime the delta time
     */
    @Override
    protected void process(final Entity entity, final double deltaTime, final World world) {
        final var pos = (PositionComponent) entity.getComponent(PositionComponent.class);
        final var mov = (MovementComponent) entity.getComponent(MovementComponent.class);
        pos.updateLastPos();
        if (mov.isEnabled()) {
            pos.setPos(pos.getPos().add(mov.getDir().multiply(mov.getSpeed() * deltaTime)));
        }
    }
}
