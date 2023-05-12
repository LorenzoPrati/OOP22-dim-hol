package dimhol.components;

import dimhol.logic.ai.AbstractAction;
import dimhol.logic.ai.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Enemy's AI.
 */
public final class AiComponent implements Component {

    private final List<Action> actions;
    private double currentTime;
    private double prevTime;

    /**
     *
     * @param newRoutines are the enemy's behavior routines.
     */
    public AiComponent(final List<Action> newRoutines) {
        this.actions = new ArrayList<>(newRoutines);
    }

    /**
     *
     * @return enemy's routines.
     */
    public List<Action> getRoutine() {
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
