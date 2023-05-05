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
     *
     * @param entity .
     * @return true if the action is actionable.
     */
    boolean canExecute(Entity entity);

    /**
     *
     * @return new entities that enemy could create.
     */
    Optional<List<Event>> execute();

    /**
     *
     * @param player .
     */
    void setPlayer(Entity player);
}
