package dimhol.logic.player;

import dimhol.core.Input;

import java.util.Optional;

public class InteractState extends AbstractState{

    @Override
    protected void setup() {
        this.interact.setInteracting(true);
    }

    @Override
    public Optional<State> transition(Input input) {
        return Optional.of(new IdleState());
    }

    @Override
    public void execute(Input input) {

    }

    @Override
    public void exit() {
        this.interact.setInteracting(false);
    }

    @Override
    public void updateAnimation() {
        this.setAnimationState("interact");
    }
}
