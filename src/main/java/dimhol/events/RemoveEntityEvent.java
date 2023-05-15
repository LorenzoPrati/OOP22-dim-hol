package dimhol.events;

import dimhol.core.World;
import dimhol.entity.Entity;

import java.util.UUID;

/**
 * Gets called when a new entity needs to be removed to the world.
 */
public class RemoveEntityEvent implements WorldEvent {

    /**
     * The id of the entity to remove.
     */
    private final UUID id;

    /**
     * Constructs a new RemoveEntityEvent.
     *
     * @param entity the entity that needs to be removed from the world
     */
    public RemoveEntityEvent(final Entity entity) {
        this.id = entity.getId();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final World world) {
        var toRemove = world.getEntities().stream()
                .filter(e -> e.getId().equals(this.id))
                .findAny();
        toRemove.ifPresent(world::removeEntity);
        //todo record stats
    }
}
