package dimhol.components;

import dimhol.entity.Entity;
import dimhol.logic.effects.Effect;

import java.util.ArrayList;
import java.util.List;

public final class AttackComponent implements Component {

    private final Entity entity;
    private final List<Effect> effects;

    public AttackComponent(final Entity entity, final List<Effect> effects) {
        this.entity = entity;
        this.effects = new ArrayList<>(effects);
    }

    public Entity getEntity() {
        return entity;
    }

    public List<Effect> getEffects() {
        return new ArrayList<>(effects);
    }
}
