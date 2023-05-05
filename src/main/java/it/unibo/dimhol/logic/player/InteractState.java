package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.core.Input;
import it.unibo.dimhol.components.InteractorComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class InteractState extends AbstractPlayerState {

    public InteractState() {
        super("interact");
    }

    @Override
    public Optional<PlayerState> transition(Input input) {
        return Optional.of(new IdleState());
    }

    @Override
    public List<Event> execute(Input input, Entity e) {
        var mov = (MovementComponent) e.getComponent(MovementComponent.class);
        var interactor = (InteractorComponent) e.getComponent(InteractorComponent.class);
        interactor.setInteracting(true);
        return Collections.emptyList();
    }

    @Override
    public void updateTime(double dt) {

    }

    @Override
    public void exit(Entity e) {
        var interactor = (InteractorComponent) e.getComponent(InteractorComponent.class);
        interactor.setInteracting(false);
    }
}
