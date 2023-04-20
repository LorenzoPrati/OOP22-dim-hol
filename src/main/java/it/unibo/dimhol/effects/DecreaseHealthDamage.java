package it.unibo.dimhol.effects;

import it.unibo.dimhol.entity.Entity;

public class DecreaseHealthDamage implements Effect {

    private final int damage;
    public DecreaseHealthDamage(Entity entity) {
        this.damage = entity.
    }

    @Override
    public boolean canUseOn(Entity entity) {
        return false;
    }

    @Override
    public void applyOn(Entity entity) {

    }
}
