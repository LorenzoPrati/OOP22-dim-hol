package dimhol.systems;

import dimhol.components.MeleeComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;

/**
 * Remove melee attacks.
 */
public final class ClearAttackSystem extends AbstractSystem {

    /**
     * Construct a ClearAttackSystem.
     */
    public ClearAttackSystem() {
        super(MeleeComponent.class);
    }

    @Override
    protected void process(final Entity entity, final double deltaTime, final World world) {
        world.notifyEvent(new RemoveEntityEvent(entity));
    }
}
