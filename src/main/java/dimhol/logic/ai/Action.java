package dimhol.logic.ai;

import dimhol.entity.Entity;
import dimhol.events.WorldEvent;

import java.util.List;
import java.util.Optional;

/**
 * This Action interface defines the necessary methods to perform
 * an AI action within the game.
 * The action can only be executed if the canExecute() method returns true.
 * The execution of the action returns an optional list of WorldEvent.
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
     * @param player .
     */
    void setPlayer(Entity player);

    /**
     * Set enemy and his components.
     * @param enemy .
     */
    void setEnemy(Entity enemy);
}
