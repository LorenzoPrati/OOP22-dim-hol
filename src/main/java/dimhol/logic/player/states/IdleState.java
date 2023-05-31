package dimhol.logic.player.states;

import dimhol.input.Input;
import dimhol.entity.Entity;
import dimhol.logic.player.AbstractState;
import dimhol.logic.player.PlayerState;

import java.util.Optional;

/**
 * Models a state where the player stands still on the ground.
 */
public class IdleState extends AbstractState {

    /**
     * Disable movement on entry.
     */
    @Override
    public void entry(final Entity entity) {
        super.entry(entity);
        this.getMov().setEnabled(false);
    }

    /**
     * {@inheritDoc}
     */
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
        if (input.isMoving()) {
            return Optional.of(new WalkState());
        }
        return Optional.empty();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAnimation() {
        super.setAnimationState("idle");
    }
}
