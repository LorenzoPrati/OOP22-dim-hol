package dimhol.logic.ai;

import dimhol.entity.Entity;
import dimhol.events.Event;

import java.util.List;
import java.util.Optional;

/**
 * Represents the logic execution of an action.
 */
public interface Action {

    /**
     * @return true if the action is actionable.
     */
    default boolean canExecute() {
        return false;
    }

    /**
     *
     * @return new entities that enemy could create.
     */
    Optional<List<Event>> execute();

}
