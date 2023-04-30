package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.logic.util.DirectionUtil;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.view.InputListener;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class IdleState extends AbstractPlayerState {

    @Override
    public Optional<PlayerState> transition(InputListener input) {
        if (input.isShooting()) {
            return Optional.of(new ShootState());
        }
        if (input.isAttacking()) {
            return Optional.of(new AttackState());
        }
        if (input.isMoving()) {
            return Optional.of(new WalkState());
        }
        return Optional.empty();
    }

    @Override
    public List<Event> update(InputListener input, Entity e, double dt) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        mov.setEnabled(false);
        this.setDesc("idle " + DirectionUtil.getStringFromVec(mov.getDir()));
        return Collections.emptyList();
    }
}
