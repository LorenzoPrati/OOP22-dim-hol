package dimhol.logic.player;

import dimhol.core.Input;

import java.util.Optional;

public class FireballState extends AbstractState {

    @Override
    protected void setup() {

    }

    @Override
    public Optional<State> transition(Input input) {
        return input.isChargingFireball()
                ? Optional.empty()
                : Optional.of(new IdleState());

    }

    @Override
    public void execute(Input input) {
        System.out.println("sparo");
    }

    @Override
    public void exit() {

    }


    @Override
    public void updateAnimation() {
        this.setAnimationState("shoot");
    }
}
