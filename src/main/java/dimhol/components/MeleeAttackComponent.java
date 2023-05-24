package dimhol.components;

import dimhol.entity.Entity;
import java.util.function.Predicate;

public final class MeleeAttackComponent extends AbstractAttackComponent {

    public MeleeAttackComponent(int damage, Predicate<Entity> predicate) {
        super(damage, predicate);
    }
}
