package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.components.AnimationComponent;

/**
 * A system wich increases and resets the indexes used for animation and the counters.
 */
public final class AnimationSystem extends AbstractSystem {
    private static final int ANIMATION_SPEED = 5;

    /**
     * Constructs a system with operates on all the entities wich have animation component.
     */
    public AnimationSystem() {
        super(AnimationComponent.class);
    }

    @Override
    protected void process(final Entity entity, final double deltaTime, final World world) {
        var animationComp = (AnimationComponent) entity.getComponent(AnimationComponent.class);
        var currentState = animationComp.getState();
        if (animationComp.isBlocking()) {
            animationComp.setCompleted(false);
        }
        if (currentState.equals(animationComp.getLastState())) {
            if (animationComp.getIndex() == animationComp.getMaxIndex() - 1) {
                if (animationComp.isBlocking()) {
                    animationComp.setCompleted(true);
                }
                animationComp.setIndex(0);
                animationComp.setCounter(0);
            } else {
                if (animationComp.getCounter() >= ANIMATION_SPEED) { 
                    animationComp.setIndex(animationComp.getIndex() + 1);
                    animationComp.setCounter(0);
                }
            }
        } else {
            animationComp.setIndex(0);
            animationComp.setCounter(0);
        }
        animationComp.setCounter(animationComp.getCounter() + 1);
    }
}
