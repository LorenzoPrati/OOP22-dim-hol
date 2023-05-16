package dimhol.logic.player;

import dimhol.core.Input;

import java.util.Optional;

public class AreaAttackState extends AbstractState {

    @Override
    protected void setup() {

    }

    @Override
    public Optional<State> transition(Input input) {
        return input.isSpecialMeele()
                ? Optional.empty()
                : Optional.of(new IdleState());
    }

    @Override
    public void execute(Input input) {
        System.out.println("earth cleaver");
    }

    @Override
    public void exit() {

    }

    @Override
    public void updateAnimation() {
        this.setAnimationState("special");
    }
}
