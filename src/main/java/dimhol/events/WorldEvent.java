package dimhol.events;

import dimhol.core.World;

/**
 * Represents an event occurred in the world to be
 * handled asynchronously from the systems update cycle.
 */
public interface WorldEvent {

    /**
     * Executes the world event.
     *
     * @param world the world where the event is occurred
     */
    void execute(World world);
}
