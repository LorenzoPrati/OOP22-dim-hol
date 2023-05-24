package dimhol.events;

import dimhol.core.World;

/**
 * Gets called when a new room needs to be generated.
 */
public class ChangeLevelEvent implements WorldEvent {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final World world) {
        world.changeLevel();
    }
}
