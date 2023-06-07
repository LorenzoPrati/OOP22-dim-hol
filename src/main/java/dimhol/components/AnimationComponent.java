package dimhol.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * A component which contains all the information necessary for animation.
 */
public class AnimationComponent implements Component {
    private final Map<String,ArrayList<Integer>> map = new HashMap<>();
    private String state; 
    private String lastState;
    private int index; 
    private int counter;
    private boolean completed;

    /**
     * Sets the initial state of the entity and the map cointaining all the information about that kind of entity.
     */
    public AnimationComponent(final Map<String,ArrayList<Integer>> map, final String initialState) {
        this.index = 0;
        this.map.putAll(map);
        this.state = initialState;
        this.lastState = state;
    }
    
    /**
     * .
     * @return
     */
    public String getState() {
        return this.state;
    }

    /**
     * .
     * @return
     */
    public String getLastState() {
        return this.lastState;
    }

    /**
     * .
     * @param state
     */
    public void setState(final String state) {
        this.lastState = this.state;
        this.state = state;
    }
    
    /**
     * .
     * @return
     */
    public int getIndex() {
        return this.index;
    }

    /**
     * .
     * @param index
     */
    public void setIndex(final int index) {
        this.index = index;
    }

    /**
     * 
     */
    public int getCounter() {
        return this.counter;
    }

    /**
     * 
     * @param counter
     */
    public void setCounter(final int counter) {
        this.counter = counter;
    }

    /**
     * .
     */
    public Map<String, ArrayList<Integer>> getMap() {
        return Collections.unmodifiableMap(this.map);
    }
    
    /**
     * 
     * @return
     */
    public boolean isCompleted() {
        return this.completed;
    }

    /**
     * 
     * @param completed
     */
    public void setCompleted(final boolean completed) {
        this.completed = completed;
    }

    /**
     * 
     * @return
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
     * 
     * @return
     */
    public int getImage() {
        return map.entrySet().stream()
                .filter(e -> e.getKey().equals(state))
                .map(Map.Entry::getValue)
                .findAny()
                .get()
                .get(1);
    }

    /**
     * 
     * @return
     */
    public boolean isBlocking() {
        var flag = map.entrySet().stream()
            .filter(e -> e.getKey().equals(state))
            .map(e -> e.getValue())
            .findAny()
            .get()
            .get(2)
            .equals(1);
        return flag;
    }
}
