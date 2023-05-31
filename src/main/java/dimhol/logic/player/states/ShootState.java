package dimhol.logic.player.states;

import dimhol.input.Input;
import dimhol.entity.Entity;
import dimhol.entity.factories.PlayerAttackFactory;
import dimhol.logic.player.AbstractState;
import dimhol.logic.player.PlayerState;

import java.util.List;
import java.util.Optional;

/**
 * Models a state where the player shoots.
 */
public class ShootState extends AbstractState {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PlayerState> transition(final Input input) {
        return input.isShooting() ? Optional.empty() : Optional.of(new IdleState());
    }

    /**
     * Executes the shooting by returning the bullet.
     *
     * @param input the user input
     * @return a list containing the entity representing the generated bullet
     */
    @Override
    public List<Entity> execute(final Input input) {
        return List.of(new PlayerAttackFactory().createLittleBulletAttack(super.getPlayerEntity()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAnimation() {
        this.setAnimationState("shoot");
    }
}
