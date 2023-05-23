package dimhol.components;

import java.util.Optional;

import dimhol.logic.effects.Effect;

public class InteractableComponent implements Component {
    private Effect effect;
    private boolean isPickable;
    private Optional<Integer> price;

    public InteractableComponent(Effect effect, boolean isPickable, Optional<Integer> price) {
        this.effect = effect;
        this.isPickable = isPickable;
        this.price = price;
    }

    public Effect getEffect() {
        return effect;
    }
    
    public boolean isPickable() {
        return this.isPickable;
    }

    public Optional<Integer> getPrice() {
        return this.price;
    }

    public boolean hasPriceToPay(){
        return this.price != null;
    }

}