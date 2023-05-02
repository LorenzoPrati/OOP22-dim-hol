package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.logic.util.DirectionUtil;
import it.unibo.dimhol.view.InputListener;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChargeState extends AbstractPlayerState {

    private double counter = 0;
    private boolean ready;

    @Override
    public Optional<PlayerState> transition(InputListener input) {
        if (!ready) {
            return input.isChargingFireball() ? Optional.empty() : Optional.of(new IdleState());
        } else {
            return input.isChargingFireball() ? Optional.empty() : Optional.of(new FireballState());
        }
    }

    @Override
    public List<Event> execute(InputListener input, Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        mov.setEnabled(false);
        this.setDesc("charge " + DirectionUtil.getStringFromVec(mov.getDir()));
        if (counter >= 3) {
            System.out.println(ready);
            counter = 0;
            ready = true;
        }
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {
        counter = counter + dt;
    }
}
