package it.unibo.dimhol.components;

import it.unibo.dimhol.properties.NumSpriteProperty;

public class InfoAnimationComponent implements Component{
    private String state; 
    private String lastState;
    private String type;
    private int index; 
    private NumSpriteProperty numSprites = new NumSpriteProperty();

    public InfoAnimationComponent(final String type,final String state){
        this.index = 0;
        this.type = type;
        this.state = state; 
        this.lastState = state;
    }

    public String getState() {
        return this.state;
    }
    
    public void setState(final String state) {
        this.lastState = state;
        this.state = state;
    }

    public String getLastState() {
        return this.lastState;
    }

    public void setLastState(final String lastState) {
        this.lastState = lastState;
    }

    public String getType() {
        return this.type;
    }

    public void setType(final String type) {
        this.type = type;
    }

    public int getIndex(){
        if(state.equals(this.lastState)){
            System.out.println(this.type);
            var maxIndex = NumSpriteProperty.getNumSprites(this.type);
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
    
}
