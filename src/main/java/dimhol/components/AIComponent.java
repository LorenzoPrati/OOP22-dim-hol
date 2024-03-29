package dimhol.components;

import dimhol.logic.ai.Action;

import java.util.ArrayList;
import java.util.List;

/**
 * Enemy's AI component contains the AI behaviour.
 */
public class AIComponent implements Component {

    private final List<Action> actions;
    private double currentTime;
    private double prevTime;

    /**
     *
     * @param newRoutines are the enemy's behavior routines.
     */
    public AIComponent(final List<Action> newRoutines) {
        this.actions = new ArrayList<>(newRoutines);
    }

    /**
     *
     * @return enemy's routines.
     */
    public List<Action> getRoutine() {
        return new ArrayList<>(actions);
    }

    /**
     * Update the current time.
     * @param currentTime
     */
    public void updateTime(final double currentTime) {
        this.currentTime += currentTime;
    }

    /**
     * Previous time getter.
     * @return previous time
     */
    public double getPrevTime() {
        return this.prevTime;
    }

    /**
     * Previous time setter.
     * @param prevTime
     */
    public void setPrevTime(final double prevTime) {
        this.prevTime = prevTime;
    }

    /**
     * Current time getter.
     * @return current time
     */
    public double getCurrentTime() {
        return this.currentTime;
    }
}
