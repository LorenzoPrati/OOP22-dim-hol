package it.unibo.dimhol.components;

import java.util.HashMap;
import java.util.Map;
import it.unibo.dimhol.StateInfo;

public class InfoAnimationComponent implements Component{
    private String state; 
    private String lastState;
    private int index; 
    private final Map<String,StateInfo> map = new HashMap<>();

    public InfoAnimationComponent(final Map<String,StateInfo> map){
        this.index = 0;
        this.map.putAll(map);
        this.state = "walking up";
    }
    
    public void setState(final String state) {
        this.lastState = state;
        this.state = state;
    }
    
    public int getIndex(){
        if(state.equals(this.lastState)){
            /*int maxIndex = 0;
            for(var elem : map.entrySet()){
                if(elem.getKey() == this.state){
                    maxIndex = elem.getValue()[0];
                }
            }*/
            var maxIndex=  map.entrySet().stream()
                .filter(e -> e.getKey().equals(this.state))
                .map(e -> e.getValue().getFrames()).findAny().get();

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
        System.out.println(index);
        return this.index;
    }

    public int getNumToUse(){
        /*int num = 0;
        for(var elem : map.entrySet()){
            if(elem.getKey() == this.state){
                num = elem.getValue()[1];
            }
        }
        return num;*/
        return map.entrySet().stream()
        .filter(e -> e.getKey().equals(this.state))
        .map(e -> e.getValue().getNum()).findAny().get();
    }
    
}
