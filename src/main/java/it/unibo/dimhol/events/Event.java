package it.unibo.dimhol.events;

import it.unibo.dimhol.core.World;

/**
 * Represents an event occurred in the world.
 */
public interface Event {
    void execute(World w);
}
