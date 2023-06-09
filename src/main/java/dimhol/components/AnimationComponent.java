package dimhol.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A component which contains all the information necessary for animation.
 */
public class AnimationComponent implements Component {
    private final Map<String, ArrayList<Integer>> map = new HashMap<>();
    private String state; 
    private String lastState;
    private int index; 
    private int counter;
    private boolean completed;

   /**
    * Sets the initial state of the entity and the map cointaining all the information about that kind of entity.
    * @param map
    * @param initialState
    */
    public AnimationComponent(final Map<String, ArrayList<Integer>> map, final String initialState) {
        this.index = 0;
        this.map.putAll(map);
        this.state = initialState;
        this.lastState = state;
    }

    /**
     * @return the current state of the entity.
     */
    public String getState() {
        return this.state;
    }

    /**
     * @return the previous state of the entity.
     */
    public String getLastState() {
        return this.lastState;
    }

    /**
     * A method used to set the new state of the entity.
     * @param state
     */
    public void setState(final String state) {
        this.lastState = this.state;
        this.state = state;
    }

    /**
     * @return the current index.
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * A method used to set the index.
     * @param index
     */
    public void setIndex(final int index) {
        this.index = index;
    }

    /**
     * @return the counter used to control the animation speed.
     */
    public int getCounter() {
        return this.counter;
    }

    /**
     * A method to set the counter.
     * @param counter
     */
    public void setCounter(final int counter) {
        this.counter = counter;
    }

    /**
     * @return the map containing the information about the animation of the entity.
     */
    public Map<String, ArrayList<Integer>> getMap() {
        return Collections.unmodifiableMap(this.map);
    }

    /**
     * @return true if the the index has reached the maximum index.
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * A method to set the animation completed.
     * @param completed
     */
    public void setCompleted(final boolean completed) {
        this.completed = completed;
    }

    /**
     * @return the maximum index of sprites of the animation.
     */
    public int getMaxIndex() {
        return map.entrySet().stream()
            .filter(e -> e.getKey().equals(state))
            .map(e -> e.getValue())
            .findAny()
            .get()
            .get(0);
    }

    /**
     * @return number of the image that has to be used.
     */
    public int getImageNumber() {
        return map.entrySet().stream()
                .filter(e -> e.getKey().equals(state))
                .map(Map.Entry::getValue)
                .findAny()
                .get()
                .get(1);
    }

    /**
     * @return true if the animation is blocking.
     */
    public boolean isBlocking() {
        return map.entrySet().stream()
            .filter(e -> e.getKey().equals(state))
            .map(e -> e.getValue())
            .findAny()
            .get()
            .get(2)
            .equals(1);
    }
}
