package it.unibo.dimhol.components;

import it.unibo.dimhol.logic.effects.Effect;
import it.unibo.dimhol.entity.Entity;

import java.util.List;

public class AttackComponent implements Component {

    private final Entity entity;
    private final List<Effect> effets;

    public AttackComponent(final Entity entity, List<Effect> effets) {
        this.entity = entity;
        this.effets = effets;
    }

    public Entity getEntity() {
        return entity;
    }

    public List<Effect> getEffets() {
        return effets;
    }
}
