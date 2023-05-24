package dimhol.components;

import dimhol.entity.Entity;

import java.util.function.Predicate;

public class AttackComponent implements Component {

    private final int damage;
    private final Predicate<Entity> predicate;

    public AttackComponent(final int damage, final Predicate<Entity> predicate) {
        this.damage = damage;
        this.predicate = predicate;
    }

    public int getDamage() {
        return damage;
    }

    public Predicate<Entity> getPredicate() {
        return predicate;
    }

}
