package it.unibo.dimhol.systems;

import java.util.ArrayList;
import java.util.Map;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.AnimationComponent;
import it.unibo.dimhol.entity.Entity;

public class AnimationSystem extends AbstractSystem {

    public AnimationSystem(World w) {
        super(w, AnimationComponent.class);
        
    }

    private int getMaxIndex(final String state, final Map<String,ArrayList<Integer>> map){
        return map.entrySet().stream()
            .filter(e -> e.getKey().equals(state))
            .map(e -> e.getValue())
            .findAny()
            .get()
            .get(0);
    }

    @Override
    public void process(Entity e) {
        var animationComp = (AnimationComponent)e.getComponent(AnimationComponent.class);
        var currentState = animationComp.getState();
        if(currentState.equals(animationComp.getLastState())){
            if(animationComp.getIndex() == getMaxIndex(currentState, animationComp.getMap()) - 1 ){
                animationComp.setIndex(0);
                System.out.println(animationComp.getIndex());
                animationComp.setCounter(0);
            }
            else{
                if(animationComp.getCounter() >= 5){
                    System.out.println(animationComp.getIndex());
                    animationComp.setIndex(animationComp.getIndex() + 1);
                    animationComp.setCounter(0);
                }
            }
        }
        else{
            animationComp.setIndex(0);
            animationComp.setCounter(0);
        }
        animationComp.setCounter(animationComp.getCounter() + 1);
    }
    
}
