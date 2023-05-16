package dimhol.logic.player;

import dimhol.core.Input;

import java.util.Optional;

public class IdleState extends AbstractState {

    @Override
    public void setup() {
        this.mov.setEnabled(false);
    }

    @Override
    public Optional<State> transition(Input input) {
        if (input.isInteracting()) {
            return Optional.of(new InteractState());
        }
        if (input.isChargingFireball()) {
            return Optional.of(new ChargeFireballState());
        }
        if (input.isShooting()) {
            return Optional.of(new ShootState());
        }
        if (input.isSpecialMeele()) {
            return Optional.of(new AreaAttackState());
        }
        if (input.isNormalMeele()) {
            return Optional.of(new SwordState());
        }
        if (input.isMoving()) {
            return Optional.of(new WalkState());
        }
        return Optional.empty();
    }

    @Override
    public void execute(Input input) {

    }


    @Override
    public void exit() {

    }

    @Override
    public void updateAnimation() {
        this.setAnimationState("idle");
    }
}
