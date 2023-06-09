package dimhol.components;

import dimhol.entity.Entity;

import java.util.function.Predicate;

/**
 * This class contains the weapon damage and the predicate that define who can receive the damage.
 */
public class AttackComponent implements Component {

    private final int damage;
    private final Predicate<Entity> predicate;

    /**
     * Constructs an AttackComponent.
     * @param damage to set
     * @param predicate define who can receive the damage
     */
    public AttackComponent(final int damage, final Predicate<Entity> predicate) {
        this.damage = damage;
        this.predicate = predicate;
    }

    /**
     * Damage getter.
     * @return damage value
     */
    public int getDamage() {
        return damage;
    }

    /**
     * Predicate getter.
     * @return predicate
     */
    public Predicate<Entity> getPredicate() {
        return predicate;
    }

}
