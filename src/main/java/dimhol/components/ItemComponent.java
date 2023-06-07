package dimhol.components;

import java.util.List;
import java.util.function.BiFunction;
import dimhol.entity.Entity;

/**
 * A component for.
 */
public class ItemComponent implements Component {
    private BiFunction<Entity, List<Class<? extends Component>>, Boolean> effect; 

    /**
     * 
     * @param effect .
     */
    public ItemComponent(BiFunction<Entity, List<Class<? extends Component>>, Boolean> effect){
        this.effect = effect;
    }

    public boolean applyEffect(Entity entity, List<Class<? extends Component>> component){
        return this.effect.apply(entity, component);
    }  
}
