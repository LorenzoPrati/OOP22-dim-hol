package dimhol.logic.player.states;

import dimhol.input.Input;
import dimhol.entity.Entity;
import dimhol.logic.player.AbstractState;
import dimhol.logic.player.PlayerState;

import java.util.Optional;

/**
 * Models a state where the player interacts with objects.
 */
public class InteractState extends AbstractState {

    /**
     * Enables the interaction.
     */
    @Override
    public void entry(final Entity entity) {
        super.entry(entity);
        super.getInteractor().setInteracting(true);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PlayerState> transition(final Input input) {
        return Optional.of(new IdleState());
    }

    /**
     * Disables the interaction.
     */
    @Override
    public void exit() {
        super.getInteractor().setInteracting(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAnimation() {
        this.setAnimationState("interact");
    }
}
