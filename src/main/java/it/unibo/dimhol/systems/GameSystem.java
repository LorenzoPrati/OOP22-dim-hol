package it.unibo.dimhol.systems;

/**
 * Represents a system which is responsible for handling the game logic i.e. acts as a sort of controller.
 */
public interface GameSystem {

    /**
     * This method gets called every world update cycle.
     */
    void update();
}
