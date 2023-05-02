package it.unibo.dimhol.events;

import it.unibo.dimhol.core.World;
import it.unibo.dimhol.entity.Entity;

/**
 * This event gets called when a new entity needs to be added to the world.
 */
public class AddEntityEvent implements Event {

    private final Entity e;

    public AddEntityEvent(final Entity e) {
        this.e = e;
    }

    @Override
    public void execute(World w) {
        w.addEntity(e);
    }
}
