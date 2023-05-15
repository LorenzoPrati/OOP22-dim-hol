package dimhol.events;

import dimhol.core.World;

/**
 * Gets called when the game is over.
 */
public class GameOverEvent implements WorldEvent {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final World world) {
        world.setGameOver();
    }
}
