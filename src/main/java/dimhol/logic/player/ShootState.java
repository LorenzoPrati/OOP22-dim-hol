package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;
import dimhol.entity.factories.PlayerAttackFactory;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ShootState extends AbstractState {

    @Override
    protected void setup() {

    }

    @Override
    public Optional<State> transition(Input input) {
        return input.isShooting()
                ? Optional.empty()
                : Optional.of(new IdleState());
    }


    @Override
    public List<Entity> execute(Input input) {
        return List.of(new PlayerAttackFactory().createLittleBulletAttack(this.playerEntity));
    }

    @Override
    public void exit() {

    }

    @Override
    public void updateAnimation() {
        this.setAnimationState("shoot");
    }
}
