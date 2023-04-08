package it.unibo.dimhol;

/**
 * Represents an event occurred in the world.
 */
public interface Event {
    void execute(World w);
}
