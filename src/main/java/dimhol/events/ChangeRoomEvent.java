package dimhol.events;

import dimhol.core.World;

/**
 * Gets called when a new room needs to be generated.
 */
public class ChangeRoomEvent implements WorldEvent {

    /**
     * {@inheritDoc}
     */
    @Override
    public void execute(final World world) {
//       world.getLevelManager()
//               .changeLevel(world.getEntities())
//               .forEach(world::addEntity);
        world.changeLevel();
    }
}
