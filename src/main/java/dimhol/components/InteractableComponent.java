package dimhol.components;

import java.util.function.BiFunction;
import dimhol.core.World;
import dimhol.entity.Entity;

public class InteractableComponent implements Component {
    private BiFunction<Entity, World, Boolean> effect;

    public InteractableComponent(BiFunction<Entity, World, Boolean> effect) {
        this.effect = effect;
    }

    public boolean applyEffect(Entity entity, World world){
        return this.effect.apply(entity, world);
    } 
}