package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;
import dimhol.entity.factories.PlayerAttackFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class SwordState extends AbstractState {

    @Override
    public void setup() {

    }

    @Override
    public Optional<State> transition(Input input) {
        if (input.isNormalMelee()) {
            return Optional.empty();
        }
        return Optional.of(new IdleState());
    }

    @Override
    public List<Entity> execute(Input input) {
        return List.of(new PlayerAttackFactory().createMeleeAttack(this.playerEntity));
    }


    @Override
    public void exit() {

    }

    @Override
    public void updateAnimation() {
        this.setAnimationState("normal");
    }
}
