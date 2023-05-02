package it.unibo.dimhol.components;

import it.unibo.dimhol.logic.effects.Effect;

public class InteractableComponent implements Component {

    private final Effect effect;

    public InteractableComponent(final Effect eff) {
        this.effect = eff;
    }

    public Effect getEffect() {
        return this.effect;
    }
}
