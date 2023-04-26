package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.components.HealthComponent;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.RemoveEntityEvent;

/**
 * A system that removes entities from the game world if they are dead.
 * An entity is considered dead if its HealthComponent's current health is equal to or less than 0.
 */
public class DeleteDeathEntitiesSystem extends AbstractSystem {
    /**
     * Constructs a system to operates on a given world and family of components.
     *
     * @param w     the world
     * @param comps the family of components
     */
    public DeleteDeathEntitiesSystem(World w, Class<? extends Component>... comps) {
        super(w, HealthComponent.class, PlayerComponent.class);
    }

    @Override
    public void process(Entity e, double dt) {
        HealthComponent hp = (HealthComponent) e.getComponent(HealthComponent.class);
        if (hp.getCurrentHealth() <= 0) {
            world.notifyEvent(new RemoveEntityEvent(e));
        }
    }
}
