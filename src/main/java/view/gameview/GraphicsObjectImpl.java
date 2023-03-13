package view.gameview;

import java.awt.image.BufferedImage;

import model.common.ObjectType;
import model.common.State;
import view.ResourceLoarder;
import view.gameview.animations.Animation;
import view.gameview.animations.AnimationImpl;

public class GraphicsObjectImpl implements GraphicObject{
    private int x; 
    private int y;
    private Animation animation;
    private ObjectType type; 
    private State state;
    private ResourceLoarder r;

    public GraphicsObjectImpl(final double x2, final double y2,final State s,final ObjectType t, final ResourceLoarder l){
        this.type = t;
        this.x = (int)x2 * l.getWidthImage(t);
        this.y = (int)y2 * l.getHeightImage(t);
        this.state = s;
        this.r = l; 
        this.animation = new AnimationImpl(r,s,t);
    }

    @Override
    public int getX() {
        return this.x;
    }

    @Override
    public int getY() {
        return this.y;
    }

    @Override
    public BufferedImage getImage() {
        return this.animation.getFrame();
    }

    @Override
    public boolean changedState(final State newState) {
        return this.state == newState;
    }

    @Override
    public void setState(final State newState) {
        this.state = newState;
    }

    @Override
    public ObjectType getType() {
        return this.type;
    }

    @Override
    public void setX(double x, ObjectType t) {
        this.x = (int)x * r.getWidthImage(t);
    }

    @Override
    public void setY(double y, ObjectType t) {
       this.y = (int)y * r.getHeightImage(t);
    }

    @Override
    public void setAnimation(Animation a) {
        this.animation = a;
    }
    
}
