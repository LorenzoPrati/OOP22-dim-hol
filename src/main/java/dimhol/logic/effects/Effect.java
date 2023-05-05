package dimhol.logic.effects;

import dimhol.entity.Entity;

public interface Effect {
    boolean canUseOn(Entity entity);

    void applyOn(Entity entity);
}
