package it.unibo.dimhol.components;

import it.unibo.dimhol.logic.ai.AbstractAction;

import java.util.List;

/**
 * Enemy's AI.
 */
public final class AiComponent implements Component {

    private final List<AbstractAction> actions;

    private long prevMovTime = System.currentTimeMillis();

    /**
     *
     * @param newRoutines are the enemy's behavior routines.
     */
    public AiComponent(final List<AbstractAction> newRoutines) {
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
    public List<AbstractAction> getRoutine() {
        return actions;
    }
}
