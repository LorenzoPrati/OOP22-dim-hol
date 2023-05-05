package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;
import dimhol.events.Event;

import java.util.List;
import java.util.Optional;

public interface PlayerState {

    Optional<PlayerState> transition(Input input);

    List<Event> execute(Input input, Entity e);

    String getDesc();

    void updateTime(double dt);

    void exit(Entity e);
}
