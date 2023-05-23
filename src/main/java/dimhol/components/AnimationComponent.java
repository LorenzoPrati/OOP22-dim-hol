package dimhol.components;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class AnimationComponent implements Component{
    private final Map<String,ArrayList<Integer>> map = new HashMap<>();
    private String state; 
    private String lastState;
    private int index; 
    private int counter;
    private boolean completed;

    public AnimationComponent(final Map<String,ArrayList<Integer>> map, String initialState){
        this.index = 0;
        this.map.putAll(map);
        this.state = initialState;
        this.lastState = state;
    }
    
    public String getState() {
        return this.state;
    }

    public String getLastState() {
        return this.lastState;
    }

    public void setState(final String state) {
        this.lastState = this.state;
        this.state = state;
    }
    
    public int getIndex(){
        return this.index;
    }

    public void setIndex(final int index) {
        this.index = index;
    }

    public int getCounter() {
        return this.counter;
    }

    public void setCounter(final int counter) {
        this.counter = counter;
    }

    public Map<String, ArrayList<Integer>> getMap() {
        return Collections.unmodifiableMap(this.map);
    }
    
    public boolean isCompleted(){
        return this.completed;
    }

    public void setCompleted(boolean completed){
        this.completed = completed;
    }

    public int getMaxIndex(){
        return map.entrySet().stream()
            .filter(e -> e.getKey().equals(state))
            .map(e -> e.getValue())
            .findAny()
            .get()
            .get(0);
    }

    public boolean isBlocking(){
        var flag = map.entrySet().stream()
            .filter(e -> e.getKey().equals(state))
            .map(e -> e.getValue())
            .findAny()
            .get()
            .get(2)
            .equals(1);
        return flag;
    }

    @Override
    public String toString(){
        return "AnimationComponent{ " + 
            ", state='" + state +'\'' + 
            ", lastState='" + lastState + '\'' + 
            ", index=" + index + 
            ", counter=" + counter +
            ", completed=" + completed + 
            '}';
    }
}
