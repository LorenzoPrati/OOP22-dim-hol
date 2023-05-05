package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.core.Input;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class ShootState extends AbstractPlayerState{

    public ShootState() {
        super("shoot");
    }

    @Override
    public Optional<PlayerState> transition(Input input) {
        return input.isShooting()
                ? Optional.empty()
                : Optional.of(new IdleState());
    }

    @Override
    public List<Event> execute(Input input, Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        if (input.isShooting()) {
//            return List.of(new AddEntityEvent(new GenericFactory()
//                    .createPlayerMeleeAttack(mov.getDir().getX(), mov.getDir().getY(), e)));
        }
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {

    }

    @Override
    public void exit(Entity e) {

    }
}
