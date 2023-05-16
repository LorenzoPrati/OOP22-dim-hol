package dimhol.logic.player;

import dimhol.components.AnimationComponent;
import dimhol.components.InteractorComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PlayerComponent;
import dimhol.entity.Entity;
import dimhol.logic.util.DirectionUtil;


public abstract class AbstractState implements State {

    protected double time;
    protected Entity playerEntity;
    protected PlayerComponent playerComp;
    protected MovementComponent mov;
    protected AnimationComponent anim;
    protected InteractorComponent interact;

    @Override
    public void entry(Entity e) {
        this.updateComps(e);
        this.setup();
    }

    protected abstract void setup();

    @Override
    public void update(double dt, Entity entity) {
        this.time += dt;
        this.updateComps(entity);
    }

    @Override
    public boolean canTransition() {
        return !this.anim.isBlocking()
                || this.anim.isCompleted();
    }

    protected void setAnimationState(String s) {
        this.anim.setState(s + " " + DirectionUtil.getStringFromVec(this.mov.getDir()));
    }

    private void updateComps(Entity entity) {
        this.playerEntity = entity;
        this.playerComp = (PlayerComponent) entity.getComponent(PlayerComponent.class);
        this.mov = (MovementComponent) entity.getComponent(MovementComponent.class);
        this.anim = (AnimationComponent) entity.getComponent(AnimationComponent.class);
        this.interact =(InteractorComponent) entity.getComponent(InteractorComponent.class);
    }
}
