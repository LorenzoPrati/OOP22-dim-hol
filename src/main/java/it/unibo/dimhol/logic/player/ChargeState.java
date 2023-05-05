package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.core.Input;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;
import org.apache.commons.lang3.time.StopWatch;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ChargeState extends AbstractPlayerState {

    private double counter = 0;
    private boolean ready;

    public ChargeState() {
        super("charge");
    }

    @Override
    public Optional<PlayerState> transition(Input input) {
        if (!ready) {
            return input.isChargingFireball()
                    ? Optional.empty()
                    : Optional.of(new IdleState());
        } else {
            return input.isChargingFireball()
                    ? Optional.empty()
                    : Optional.of(new FireballState());
        }
    }

    @Override
    public List<Event> execute(Input input, Entity e) {
        if (counter >= 3) {
            counter = 0;
            ready = true;
        }
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {
        System.out.println(counter);
        counter = counter + dt;
    }

    @Override
    public void exit(Entity e) {

    }
}
