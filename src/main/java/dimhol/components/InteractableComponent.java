package dimhol.components;

import java.util.function.BiConsumer;
import dimhol.core.World;
import dimhol.entity.Entity;

public class InteractableComponent implements Component {
    private BiConsumer<Entity,World> effect;

    public InteractableComponent(BiConsumer<Entity,World> effect) {
        this.effect = effect;
    }

    public void applyEffect(Entity entity, World world){
        this.effect.accept(entity, world);
    } 
}