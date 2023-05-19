package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;

import java.util.Collections;
import java.util.List;
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
    public List<Entity> execute(Input input) {
        return Collections.emptyList();
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
