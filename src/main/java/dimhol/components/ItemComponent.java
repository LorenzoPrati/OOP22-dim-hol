package dimhol.components;

import java.util.function.BiConsumer;
import dimhol.entity.Entity;

public class ItemComponent implements Component {
    private BiConsumer<Entity, Class<? extends Component>> effect; 

    public ItemComponent(BiConsumer<Entity, Class<? extends Component>> effect){
        this.effect = effect;
    }

    public void applyEffect(Entity entity, Class<? extends Component> component){
        this.effect.accept(entity, component);
    }  
}
