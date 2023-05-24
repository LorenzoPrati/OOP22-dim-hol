package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.components.AnimationComponent;

public class AnimationSystem extends AbstractSystem {

    public AnimationSystem() {
        super(AnimationComponent.class);
        
    }

    @Override
    public void process(final Entity entity, final double dt, final World world) {
        var animationComp = (AnimationComponent)entity.getComponent(AnimationComponent.class);
        var currentState = animationComp.getState();
        if(animationComp.isBlocking()){
            animationComp.setCompleted(false);
        }
        if(currentState.equals(animationComp.getLastState())){
            if(animationComp.getIndex() == animationComp.getMaxIndex() - 1 ){
                if(animationComp.isBlocking()){
                    animationComp.setCompleted(true);
                }
                animationComp.setIndex(0);
                animationComp.setCounter(0);
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
