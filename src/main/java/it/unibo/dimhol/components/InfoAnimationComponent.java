package it.unibo.dimhol.components;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import it.unibo.dimhol.StateInfo;

public class InfoAnimationComponent implements Component{
    private String state; 
    private String lastState;
    private int index; 
    private final Map<String,Integer[]> map = new HashMap<>();

    public InfoAnimationComponent(final Map<String,Integer[]> map){
        this.index = 0;
        this.map.putAll(map);
        this.state = "walking up";
        this.lastState = state;
    }
    
    public void setState(final String state) {
        this.lastState = state;
        this.state = state;
    }
    
    public int getIndex(){
        if(state.equals(this.lastState)){
            int maxIndex = 0;
            for(var elem : map.entrySet()){
                if(elem.getKey().equals(this.state)){
                    maxIndex = elem.getValue()[0];
                }
            }
            if(this.index == maxIndex-1){ 
                this.index = 0;
            }
            else{
                this.index++;
            }
        }
        else{
            this.index = 0;
        }
        return this.index;
    }

    public int getNumToUse(){
        for(var elem : map.entrySet()){
            if(elem.getKey().equals(this.state)){
                return elem.getValue()[1];
            }
        }
        return -1;
    }
    
}
