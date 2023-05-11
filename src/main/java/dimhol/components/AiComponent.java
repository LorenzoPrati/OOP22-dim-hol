package dimhol.components;

import dimhol.logic.ai.AbstractAction;

import java.util.ArrayList;
import java.util.List;

/**
 * Enemy's AI.
 */
public final class AiComponent implements Component {

    private final List<AbstractAction> actions;
    private double currentTime;
    private double prevTime;

    /**
     *
     * @param newRoutines are the enemy's behavior routines.
     */
    public AiComponent(final List<AbstractAction> newRoutines) {
        this.actions = new ArrayList<>(newRoutines);
    }

    /**
     *
     * @return enemy's routines.
     */
    public List<AbstractAction> getRoutine() {
        return new ArrayList<>(actions);
    }

    public void updateTime(double currentTime) {
        this.currentTime += currentTime;
    }

    public double getPrevTime() {
        return this.prevTime;
    }

    public void setPrevTime(final double prevTime) {
        this.prevTime = prevTime;
    }

    public double getCurrentTime() {
        return this.currentTime;
    }
}
