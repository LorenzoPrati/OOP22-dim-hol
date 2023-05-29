package dimhol.components;

import java.util.function.BiFunction;
import dimhol.entity.Entity;

public class ItemComponent implements Component {
    private BiFunction<Entity, Class<? extends Component>, Boolean> effect; 

    public ItemComponent(BiFunction<Entity, Class<? extends Component>, Boolean> effect){
        this.effect = effect;
    }

    public boolean applyEffect(Entity entity, Class<? extends Component> component){
        return this.effect.apply(entity, component);
    }  
}
