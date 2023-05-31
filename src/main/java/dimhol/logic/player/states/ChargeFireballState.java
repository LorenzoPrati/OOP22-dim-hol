package dimhol.logic.player.states;

import dimhol.input.Input;
import dimhol.logic.player.AbstractState;
import dimhol.logic.player.PlayerState;

import java.util.Optional;

/**
 * Models a state where the player charges a fireball.
 */
public class ChargeFireballState extends AbstractState {

    /**
     * The time to wait until the fireball is ready.
     */
    private static final double WAIT_TIME = 3;

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PlayerState> transition(final Input input) {
        return super.getTime() >= WAIT_TIME
                                ? input.isChargingFireball()
                                    ? Optional.empty()
                                    : Optional.of(new FireballState())
                                : input.isChargingFireball()
                                    ? Optional.empty()
                                    : Optional.of(new IdleState());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAnimation() {
        this.setAnimationState("charge");
    }
}
