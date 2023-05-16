package dimhol.logic.player;

import dimhol.core.Input;

import java.util.Optional;

public class SwordState extends AbstractState {

    @Override
    public void setup() {

    }

    @Override
    public Optional<State> transition(Input input) {
        if (input.isNormalMeele()) {
            return Optional.empty();
        }
        return Optional.of(new IdleState());
    }

    @Override
    public void execute(Input input) {
        System.out.println("eseguo attacco melee");
    }


    @Override
    public void exit() {

    }

    @Override
    public void updateAnimation() {
        this.setAnimationState("normal");
    }
}
