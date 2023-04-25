package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.AnimationComponent;
import it.unibo.dimhol.entity.Entity;

public class AnimationSystem extends AbstractSystem {

    public AnimationSystem(World w) {
        super(w, AnimationComponent.class);
        
    }

    @Override
    public void process(Entity e) {
        var animationComp = (AnimationComponent)e.getComponent(AnimationComponent.class);
        var currentState = animationComp.getState();
        if(currentState.equals(animationComp.getLastState())){
            if(animationComp.getIndex() == animationComp.getMaxINdex(currentState) - 1){
                animationComp.setIndex(0);
            }
            else{
                if(animationComp.getCounter() >= 5){
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
