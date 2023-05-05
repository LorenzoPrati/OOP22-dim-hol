package dimhol.events;

import dimhol.core.World;

/**
 * Represents an event occurred in the world.
 */
public interface Event {
    void execute(World w);
}
