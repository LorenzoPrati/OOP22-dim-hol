package it.unibo.dimhol.logic.effects;

import it.unibo.dimhol.entity.Entity;

public interface Effect {
    boolean canUseOn(Entity entity);

    void applyOn(Entity entity);
}
