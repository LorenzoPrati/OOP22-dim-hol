package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.logic.util.DirectionUtil;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.view.InputListener;
import org.locationtech.jts.math.Vector2D;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class WalkState extends AbstractPlayerState {

    public WalkState() {
    }

    @Override
    public Optional<PlayerState> transition(InputListener input) {
        if (input.isChargingFireball()) {
            return Optional.of(new ChargeState());
        }
        if (input.isAttacking()) {
            return Optional.of(new CombatState());
        }
        if (!input.isMoving()) {
            return Optional.of(new IdleState());
        }
        return Optional.empty();
    }

    @Override
    public List<Event> execute(InputListener input, Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        mov.setEnabled(true);
        if (input.isUp()) {
            mov.setDir(new Vector2D(0, -1));
        } else if (input.isDown()) {
            mov.setDir(new Vector2D(0, 1));
        } else if (input.isLeft()) {
            mov.setDir(new Vector2D(-1, 0));
        } else if (input.isRight()) {
            mov.setDir(new Vector2D(1, 0));
        }
        this.setDesc("walk " + DirectionUtil.getStringFromVec(mov.getDir()));
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {

    }
}
