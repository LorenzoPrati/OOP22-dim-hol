package it.unibo.dimhol.logic.ai;

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
