package dimhol.systems;

import dimhol.core.World;

/**
 * Represents a system which is responsible for handling the game logic by processing entities.
 */
public interface GameSystem {

    /**
     * This method gets called every world update cycle.
     *
     * @param world the world on which the system has to operate
     * @param deltaTime the delta time
     */
    void update(World world, double deltaTime);
}
