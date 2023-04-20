package it.unibo.dimhol.components;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class InfoAnimationComponent implements Component{
    private String state; 
    private String lastState;
    private int index; 
    private final Map<String,ArrayList<Integer>> map = new HashMap<>();

    public InfoAnimationComponent(final Map<String,ArrayList<Integer>> map, String initialState){
        this.index = 0;
        this.map.putAll(map);
        this.state = initialState;
        this.lastState = null;
    }
    
    public void setState(final String state) {
        this.lastState = this.state;
        this.state = state;
    }
    
    
    public int getIndex(){
        if(state.equals(this.lastState)){
            int maxIndex = 0;
            for(var elem : map.entrySet()){
                if(elem.getKey().equals(this.state)){
                    maxIndex = elem.getValue().get(0);
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
        System.out.println("indice: " + index); //for debug
        return this.index;
    }

    public int getNumToUse(){
        int num = -1;
        for(var elem : map.entrySet()){
            if(elem.getKey().equals(this.state)){
                
                num=  elem.getValue().get(1);
                System.out.println("numero immagine: " + num); //for debug
                return num;
            }
        }
        return num;
    }
    
}
