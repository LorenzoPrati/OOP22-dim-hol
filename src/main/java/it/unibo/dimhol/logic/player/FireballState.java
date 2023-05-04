package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.core.Input;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.entity.factories.GenericFactory;
import it.unibo.dimhol.events.AddEntityEvent;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.logic.util.DirectionUtil;

import java.util.List;
import java.util.Optional;

public class FireballState extends AbstractPlayerState{

    public FireballState() {
    }

    @Override
    public Optional<PlayerState> transition(Input input) {
        return Optional.of(new IdleState());
    }

    @Override
    public List<Event> execute(Input input, Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        mov.setEnabled(false);
        this.setDesc("shoot " + DirectionUtil.getStringFromVec(mov.getDir()));
//        return List.of(new AddEntityEvent(new GenericFactory().
//        createPlayerBullet(mov.getDir().getX(), mov.getDir().getY(), e)));
        return null;
    }

    @Override
    public void updateTime(double dt) {

    }
}
