package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;
import dimhol.events.WorldEvent;
import dimhol.components.MovementComponent;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WalkState extends AbstractPlayerState {

    public WalkState() {
        super("walk");
    }

    @Override
    public Optional<PlayerState> transition(Input input) {
        if (input.isChargingFireball()) {
            return Optional.of(new ChargeState());
        }
        if (input.isShooting()) {
            return Optional.of(new ShootState());
        }
        if (input.isSpecialMeele()) {
            return Optional.of(new SwordAbilityState());
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
    public List<WorldEvent> execute(Input input, Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        mov.setEnabled(true);
        input.getDirection().ifPresent(mov::setDir);
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {

    }

    @Override
    public void exit(Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        mov.setEnabled(false);
    }
}
