package dimhol.components;

import dimhol.logic.effects.Effect;

public class InteractableComponent implements Component {
    private Effect effect;
    private boolean isPickable;

    public InteractableComponent(Effect effect, boolean isPickable) {
        this.effect = effect;
        this.isPickable = isPickable;
    }

    public Effect getEffect() {
        return effect;
    }
    
    public boolean isPickable() {
        return this.isPickable;
    }

}