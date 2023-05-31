package dimhol.logic.player.states;

import dimhol.input.Input;
import dimhol.entity.Entity;
import dimhol.logic.player.AbstractState;
import dimhol.logic.player.PlayerState;
import org.locationtech.jts.math.Vector2D;

import java.util.List;
import java.util.Optional;

/**
 * Models a state where the player walks.
 */
public class WalkState extends AbstractState {

    /**
     * Enable the movement on entry.
     */
    @Override
    public void entry(final Entity entity) {
        super.entry(entity);
        this.getMov().setEnabled(true);
    }

    @Override
    public Optional<PlayerState> transition(final Input input) {
        if (input.isInteracting()) {
            return Optional.of(new InteractState());
        }
        if (input.isChargingFireball()) {
            return Optional.of(new ChargeFireballState());
        }
        if (input.isShooting()) {
            return Optional.of(new ShootState());
        }
        if (input.isAttacking()) {
            return Optional.of(new SwordState());
        }
        if (!input.isMoving()) {
            return Optional.of(new IdleState());
        }
        return Optional.empty();
    }

    /**
     * Sets the movement direction based on the user input.
     *
     * @param input the user input
     * @return an empty list
     */
    @Override
    public List<Entity> execute(final Input input) {
        if (input.isUp()) {
            super.getMov().setDir(new Vector2D(0,-1));
        }
        if (input.isDown()) {
            super.getMov().setDir(new Vector2D(0, 1));
        }
        if (input.isLeft()) {
            super.getMov().setDir(new Vector2D(-1,0));
        }
        if (input.isRight()) {
            super.getMov().setDir(new Vector2D(1,0));
        }
        return super.execute(input);
    }

    /**
     * Disable the movement on exit.
     */
    @Override
    public void exit() {
        this.getMov().setEnabled(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAnimation() {
        this.setAnimationState("walk");
    }

}
