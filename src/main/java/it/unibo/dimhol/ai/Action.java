package it.unibo.dimhol.ai;

import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;

import java.util.List;
import java.util.Optional;

/**
 * Represents the logic execution of an action.
 */
public interface Action {

    /**
     *
     * @param player .
     * @param enemy .
     * @return true if the action is actionable.
     */
    boolean canExecute(Entity player, Entity enemy);

    /**
     *
     * @param enemy is the entity that could execute an action.
     * @return new entities that enemy could create.
     */
    Optional<List<Event>> execute(Entity enemy);

}
