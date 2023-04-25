package it.unibo.dimhol.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class AnimationComponent implements Component{
    private final Map<String,ArrayList<Integer>> map = new HashMap<>();
    private String state; 
    private String lastState;
    private int index; 
    private int counter;

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
        return this.map;
    }
    
}
