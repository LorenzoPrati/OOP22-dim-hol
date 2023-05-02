package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.Input;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.view.InputListener;

import java.util.List;
import java.util.Optional;

public interface PlayerState {

    Optional<PlayerState> transition(Input input);

    List<Event> execute(Input input, Entity e);

    String getDesc();

    void updateTime(double dt);
}
