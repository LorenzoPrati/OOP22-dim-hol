package dimhol.logic.player.states;

import dimhol.input.Input;
import dimhol.entity.Entity;
import dimhol.entity.factories.PlayerAttackFactory;
import dimhol.logic.player.AbstractState;
import dimhol.logic.player.PlayerState;

import java.util.List;
import java.util.Optional;

/**
 * Models a state where the player uses the sword to attack.
 */
public class SwordState extends AbstractState {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PlayerState> transition(final Input input) {
        return  input.isAttacking() ? Optional.empty() : Optional.of(new IdleState());
    }

    /**
     * Performs the attack by returning the corresponding entity.
     *
     * @param input the user input
     * @return a list containing the generated melee attack
     */
    @Override
    public List<Entity> execute(final Input input) {
        return List.of(new PlayerAttackFactory().createMeleeAttack(super.getPlayerEntity()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAnimation() {
        this.setAnimationState("normal");
    }
}
