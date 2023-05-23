package dimhol.logic.effects;

import java.util.Optional;

import dimhol.entity.Entity;
import dimhol.events.WorldEvent;

public interface Effect {
    boolean canUseOn(Entity entity);

    Optional<WorldEvent> applyOn(Entity entity);
}
