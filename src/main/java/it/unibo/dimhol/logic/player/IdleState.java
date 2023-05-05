package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.core.Input;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IdleState extends AbstractPlayerState {

    public IdleState() {
        super("idle");
    }

    @Override
    public Optional<PlayerState> transition(Input input) {
        if (input.isInteracting()) {
            return Optional.of(new InteractState());
        }
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
        if (input.isMoving()) {
            return Optional.of(new WalkState());
        }
        return Optional.empty();
    }

    @Override
    public List<Event> execute(Input input, Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {

    }

    @Override
    public void exit(Entity e) {

    }
}
