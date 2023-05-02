package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.components.InteractorComponent;
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
        if (input.isInteracting()) {
            return Optional.of(new InteractState());
        }
        if (input.isChargingFireball()) {
            return Optional.of(new ChargeState());
        }
        if (input.isAttacking()) {
            return Optional.of(new CombatState());
        }
        if (input.isMoving()) {
            return Optional.of(new WalkState());
        }
        return Optional.empty();
    }

    @Override
    public List<Event> execute(InputListener input, Entity e) {
        var interactor = (InteractorComponent) e.getComponent(InteractorComponent.class);
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        mov.setEnabled(false);
        interactor.setInteracting(false);
        this.setDesc("idle " + DirectionUtil.getStringFromVec(mov.getDir()));
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {

    }
}
