package dimhol.components;

import dimhol.logic.effects.Effect;

public class PickableComponent implements Component {
    private Effect effect;

    public PickableComponent(Effect effect) {
        this.effect = effect;
    }

    public Effect getEffect() {
        return effect;
    }
    
}
