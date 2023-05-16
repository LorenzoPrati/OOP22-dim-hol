package dimhol.logic.player;

import dimhol.core.Input;

import java.util.Optional;

public class ChargeFireballState extends AbstractState {

    private double waitTime = 3;

    @Override
    protected void setup() {

    }

    @Override
    public Optional<State> transition(Input input) {
        return this.time >= this.waitTime
            ? input.isChargingFireball()
                ? Optional.empty()
                : Optional.of(new FireballState())
            : input.isChargingFireball()
                ? Optional.empty()
                : Optional.of(new IdleState());
    }

    @Override
    public void execute(Input input) {

    }

    @Override
    public void exit() {

    }

    @Override
    public void updateAnimation() {
        this.setAnimationState("charge");
    }
}
