package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;

import java.util.Collections;
import java.util.List;
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
    public List<Entity> execute(Input input) {
        return Collections.emptyList();
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
