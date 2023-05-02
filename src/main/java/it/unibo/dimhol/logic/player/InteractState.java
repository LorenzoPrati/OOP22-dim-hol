package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.Input;
import it.unibo.dimhol.components.InteractorComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.logic.util.DirectionUtil;
import it.unibo.dimhol.view.InputListener;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InteractState extends AbstractPlayerState {

    @Override
    public Optional<PlayerState> transition(Input input) {
        return Optional.of(new IdleState());
    }

    @Override
    public List<Event> execute(Input input, Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        var interactor = (InteractorComponent) e.getComponent(InteractorComponent.class);
        interactor.setInteracting(true);
        this.setDesc("interact " + DirectionUtil.getStringFromVec(mov.getDir()));
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {

    }
}
