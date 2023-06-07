package dimhol.components;

import java.util.function.BiFunction;
import dimhol.core.World;
import dimhol.entity.Entity;

/**
 * A component for interactable objects.
 */
public class InteractableComponent implements Component {
    private final BiFunction<Entity, World, Boolean> effect;

    /**
     * Constructs an InteractableComponent.
     * @param effect of the interactable object.
     */
    public InteractableComponent(final BiFunction<Entity, World, Boolean> effect) {
        this.effect = effect;
    }

    /**
     * Apply the effect on the entity.
     * @param entity
     * @param world
     * @return true if the effect has been applied.
     */
    public boolean applyEffect(final Entity entity, final World world) {
        return this.effect.apply(entity, world);
    } 
}
