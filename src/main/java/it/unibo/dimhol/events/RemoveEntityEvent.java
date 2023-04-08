package it.unibo.dimhol.events;

import it.unibo.dimhol.World;
import it.unibo.dimhol.entity.Entity;

/**
 * This event gets called when a new entity needs to be removed to the world.
 */
public class RemoveEntityEvent implements Event {
    private final Entity e;

    public RemoveEntityEvent(final Entity e) {
        this.e = e;
    }

    @Override
    public void execute(World w) {
        w.removeEntity(e);
    }
}
