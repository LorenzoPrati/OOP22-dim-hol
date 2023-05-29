package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class AreaAttackState extends AbstractState {

    @Override
    protected void setup() {

    }

    @Override
    public Optional<State> transition(Input input) {
        return input.isSpecialMelee()
                ? Optional.empty()
                : Optional.of(new IdleState());
    }

    @Override
    public List<Entity> execute(Input input) {
        return Collections.emptyList();
    }

    @Override
    public void exit() {

    }

    @Override
    public void updateAnimation() {
        this.setAnimationState("special");
    }
}
