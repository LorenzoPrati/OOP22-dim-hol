package it.unibo.dimhol.components;

import it.unibo.dimhol.effects.Effect;

public class DamageComponent implements Component {

    private final Effect damageEffect;

    public DamageComponent(Effect damageEffect) {
        this.damageEffect = damageEffect;
    }

    public Effect getDamageEffect() {
        return damageEffect;
    }
}
