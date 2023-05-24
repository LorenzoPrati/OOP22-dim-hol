package dimhol.components;

import dimhol.entity.Entity;

import java.util.function.Predicate;

public class BulletAttackomponent extends AbstractAttackComponent {
    public BulletAttackomponent(int damage, Predicate<Entity> predicate) {
        super(damage, predicate);
    }
}
