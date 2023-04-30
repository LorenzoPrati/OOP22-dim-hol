package it.unibo.dimhol.logic.player;

import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.Event;
import it.unibo.dimhol.view.InputListener;

import java.util.List;
import java.util.Optional;

public interface PlayerState {

    Optional<PlayerState> transition(InputListener input);

    List<Event> update(InputListener input, Entity e, double dt);

    String getDesc();
}
