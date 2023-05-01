package it.unibo.dimhol.components;

import it.unibo.dimhol.logic.effects.Effect;

public class PickableComponent implements Component {
    private Effect effect;

    public PickableComponent(Effect effect) {
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
    
}
