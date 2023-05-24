package dimhol.components;

import dimhol.entity.Entity;
import dimhol.logic.effects.Effect;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

public final class AttackComponent implements Component {

    private final int damage;
    private final Entity entity;
    private final Predicate<Entity> predicate;

    public AttackComponent(int damage, Entity entity, Predicate<Entity> predicate) {
        this.damage = damage;
        this.entity = entity;
        this.predicate = predicate;
    }

    public int getDamage() {
        return damage;
    }

    public Entity getEntity() {
        return entity;
    }

    public Predicate<Entity> getPredicate() {
        return predicate;
    }
}
