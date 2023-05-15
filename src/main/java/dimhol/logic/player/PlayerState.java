package dimhol.logic.player;

import dimhol.core.Input;
import dimhol.entity.Entity;
import dimhol.events.WorldEvent;

import java.util.List;
import java.util.Optional;

public interface PlayerState {

    Optional<PlayerState> transition(Input input);

    List<WorldEvent> execute(Input input, Entity e);

    String getDesc();

    void updateTime(double dt);

    void exit(Entity e);
}
