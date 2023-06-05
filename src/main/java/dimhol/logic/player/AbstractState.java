package dimhol.logic.player;

import dimhol.components.AnimationComponent;
import dimhol.components.InteractorComponent;
import dimhol.components.MovementComponent;
import dimhol.input.Input;
import dimhol.entity.Entity;
import dimhol.logic.util.DirectionUtil;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

/**
 * Abstract implementation of a player state.
 */
public abstract class AbstractState implements PlayerState {

    /**
     * The time elapsed since entering the state.
     */
    private double time;
    /**
     * The player movement component.
     */
    private MovementComponent mov;
    /**
     * The player interactor component.
     */
    private InteractorComponent interactor;
    /**
     * The player animation component.
     */
    private AnimationComponent anim;
    /**
     * The entity representing the player.
     */
    private Entity playerEntity;

    /**
     * {@inheritDoc}
     */
    @Override
    public void entry(final Entity entity) {
        this.updateComps(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void update(final double deltaTime, final Entity entity) {
        this.time += deltaTime;
        this.updateComps(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean canTransition() {
        return !this.anim.isBlocking() || this.anim.isCompleted();
    }

    /**
     * {@inheritDoc}
     */
    public abstract Optional<PlayerState> transition(Input input);

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Entity> execute(final Input input) {
        return Collections.emptyList();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void exit() {

    }

    /**
     * Sets the animation.
     *
     * @param state the string representing the animation to assign
     */
    public void setAnimationState(final String state) {
        this.anim.setState(state + " " + DirectionUtil.getStringFromVec(this.mov.getDir()));
    }

    /**
     * Gets the elapsed time since entering the state.
     *
     * @return the time
     */
    public double getTime() {
        return this.time;
    }

    /**
     * Gets the movement component of the entity representing the player.
     *
     * @return the movement component
     */
    protected MovementComponent getMov() {
        return this.mov;
    }

    /**
     * Gets the interactor component of the entity representing the player.
     *
     * @return the interactor component
     */
    protected InteractorComponent getInteractor() {
        return this.interactor;
    }

    /**
     * Gets the entity representing the player.
     *
     * @return the entity representing player
     */
    protected Entity getPlayerEntity() {
        return this.playerEntity;
    }

    /**
     * Update components that will be used by the state.
     *
     * @param entity the entity representing the player
     */
    private void updateComps(final Entity entity) {
        this.playerEntity = entity;
        this.mov = (MovementComponent) entity.getComponent(MovementComponent.class);
        this.anim = (AnimationComponent) entity.getComponent(AnimationComponent.class);
        this.interactor = (InteractorComponent) entity.getComponent(InteractorComponent.class);
    }
}
