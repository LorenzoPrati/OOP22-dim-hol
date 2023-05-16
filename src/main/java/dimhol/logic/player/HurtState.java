package dimhol.logic.player;

import dimhol.core.Input;

import java.util.Optional;

public class HurtState extends AbstractState {

    @Override
    protected void setup() {
        this.mov.setEnabled(false);
    }

    @Override
    public Optional<State> transition(Input input) {
        return Optional.of(new IdleState());
    }

    @Override
    public void execute(Input input) {
        System.out.println("hurt");
    }

    @Override
    public void exit() {
        this.mov.setEnabled(true);
    }

    @Override
    public void updateAnimation() {
        this.setAnimationState("hurt");
    }
}
