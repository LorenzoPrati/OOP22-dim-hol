package dimhol.systems;

import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;
import dimhol.components.HealthComponent;

/**
 * A system that removes entities from the game world if they are dead.
 * An entity is considered dead if its HealthComponent's current health is equal to or less than 0.
 */
public class CheckHealthSystem extends AbstractSystem {

    /**
     * Constructs a system to operates on a given world and family of components.
     */
    public CheckHealthSystem() {
        super(HealthComponent.class);
    }

    @Override
    public void process(final Entity entity, final double dt, final World world) {
        HealthComponent hp = (HealthComponent) entity.getComponent(HealthComponent.class);
        if (hp.getCurrentHealth() <= 0) {
            world.notifyEvent(new RemoveEntityEvent(entity));
        }
    }
}
