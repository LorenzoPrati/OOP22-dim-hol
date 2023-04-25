package it.unibo.dimhol.components;

import it.unibo.dimhol.ai.Action;

import java.util.List;

/**
 * Enemy's AI.
 */
public final class AiComponent implements Component {

    private final List<Action> actions;

    private long prevMovTime = System.currentTimeMillis();

    /**
     *
     * @param newRoutines are the enemy's behavior routines.
     */
    public AiComponent(final List<Action> newRoutines) {
        this.actions = newRoutines;
    }

    /**
     *
     * @return prvious time.
     */
    public long getPrevMovTime() {
        return prevMovTime;
    }

    /**
     *
     * @param prevMovTime previous time to set.
     */
    public void setPrevMovTime(final long prevMovTime) {
        this.prevMovTime = prevMovTime;
    }

    /**
     *
     * @return enemy's routines.
     */
    public List<Action> getRoutine() {
        return actions;
    }
}
