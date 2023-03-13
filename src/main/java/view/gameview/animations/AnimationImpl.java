package view.gameview.animations;

import java.awt.image.BufferedImage;
import model.common.ObjectType;
import model.common.State;
import view.ResourceLoarder;

public class AnimationImpl implements Animation {
    private ImagesIterator iterator; 

    public AnimationImpl(ResourceLoarder r,State state, ObjectType type){
        this.iterator = new ImagesIterator(r.getStateImage(type, state), needLoop(state), r.getWidthImage(type), r.getHeightImage(type));
    }

    private boolean needLoop(State s){
        return !s.equals(State.ATTACKING_DOWN) 
            && !s.equals(State.ATTACKING_UP) 
            && !s.equals(State.ATTACKING_RIGHT) 
            && !s.equals(State.ATTACKING_LEFT);
    }

    @Override
    public BufferedImage getFrame() {
        return this.iterator.next();
    }

}
