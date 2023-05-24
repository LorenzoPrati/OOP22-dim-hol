package dimhol.systems;

import dimhol.components.AttackComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;

/**
 * Remove all attacks.
 */
public class ClearAttackSystem extends AbstractSystem {

    public ClearAttackSystem() {
        super(AttackComponent.class);
    }

    @Override
    public void process(final Entity entity, final double dt, final World world) {
        world.notifyEvent(new RemoveEntityEvent(entity));
    }
}
