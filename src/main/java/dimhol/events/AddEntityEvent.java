package dimhol.events;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.entity.EntityImpl;

/**
 * Gets called when a new entity needs to be added to the world.
 */
public final class AddEntityEvent implements WorldEvent {

    /**
     * The entity to add.
     */
    private final Entity entity;

    /**
     * Constructs an AddEntityEvent.
     *
     * @param entity the entity that needs to be added to the world
     */
    public AddEntityEvent(final Entity entity) {
        this.entity = new EntityImpl(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final World world) {
        world.addEntity(entity);
    }
}
