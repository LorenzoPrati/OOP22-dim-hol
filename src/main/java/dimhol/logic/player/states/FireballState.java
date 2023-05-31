package dimhol.logic.player.states;

import dimhol.input.Input;
import dimhol.entity.Entity;
import dimhol.entity.factories.PlayerAttackFactory;
import dimhol.logic.player.AbstractState;
import dimhol.logic.player.PlayerState;

import java.util.List;
import java.util.Optional;

/**
 * Models a state where the player throws a fireball.
 */
public class FireballState extends AbstractState {

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<PlayerState> transition(final Input input) {
        return input.isChargingFireball()
                ? Optional.empty()
                : Optional.of(new IdleState());

    }

    /**
     * Executes the fireball attack by returning the generated entity.
     *
     * @param input the user input
     * @return a list containing the entity representing the fireball
     */
    @Override
    public List<Entity> execute(final Input input) {
        return List.of(new PlayerAttackFactory().createBigBulletAttack(super.getPlayerEntity()));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void updateAnimation() {
        this.setAnimationState("shoot");
    }
}
