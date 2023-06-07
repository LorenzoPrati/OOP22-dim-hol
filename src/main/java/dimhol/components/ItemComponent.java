package dimhol.components;

import java.util.List;
import java.util.function.BiFunction;
import dimhol.entity.Entity;

/**
 * A component for items (pickable objects).
 */
public class ItemComponent implements Component {
    private final BiFunction<Entity, List<Class<? extends Component>>, Boolean> effect; 

    /**
     * Constructs an ItemComponent.
     * @param effect of the item.
     */
    public ItemComponent(final BiFunction<Entity, List<Class<? extends Component>>, Boolean> effect) {
        this.effect = effect;
    }

    /**
     * Tries to apply the effect on the entity.
     * @param entity
     * @param components a list of component to select entitie which can use the effect.
     * @return true if the effect has been applied.
     */
    public boolean applyEffect(final Entity entity, final List<Class<? extends Component>> components) {
        return this.effect.apply(entity, components);
    }
}
