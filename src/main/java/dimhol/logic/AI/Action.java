package dimhol.logic.AI;

import dimhol.entity.Entity;
import dimhol.events.WorldEvent;

import java.util.List;
import java.util.Optional;

/**
 * Represents the logic execution of an action.
 */
public interface Action {

    /**
     * This method check if an action is executable.
     * @return boolean
     */
    boolean canExecute();

    /**
     * This method executes an Action.
     * @return an optional list of events
     */
    Optional<List<WorldEvent>> execute();

    /**
     * Set player and his components.
     * @param player
     */
    void setPlayer(Entity player);

    /**
     * Set enemy and his components.
     * @param enemy
     */
    void setEnemy(Entity enemy);
}
