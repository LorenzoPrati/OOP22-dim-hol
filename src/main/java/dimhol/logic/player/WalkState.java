package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WalkState extends AbstractState {

    @Override
    public void setup() {
        this.mov.setEnabled(true);
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
        if (!input.isMoving()) {
            return Optional.of(new IdleState());
        }
        return Optional.empty();
    }

    @Override
    public List<Entity> execute(Input input) {
        if (input.getDirection().isPresent()) {
            this.mov.setDir(input.getDirection().get());
        }
        return Collections.emptyList();
    }

    @Override
    public void exit() {
        this.mov.setEnabled(false);
    }

    @Override
    public void updateAnimation() {
        this.setAnimationState("walk");
    }

}
