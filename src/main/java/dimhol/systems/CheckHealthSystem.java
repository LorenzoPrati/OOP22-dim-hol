package dimhol.systems;

import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;
import dimhol.components.HealthComponent;
import dimhol.components.PlayerComponent;

/**
 * A system that removes entities from the game world if they are dead.
 * An entity is considered dead if its HealthComponent's current health is equal to or less than 0.
 */
public class CheckHealthSystem extends AbstractSystem {

    /**
     * Constructs a system to operates on a given world and family of components.
     *
     * @param w     the world
     */
    public CheckHealthSystem(WorldImpl w) {
        super(w, HealthComponent.class);
    }

    @Override
    public void process(Entity e, double dt) {
        HealthComponent hp = (HealthComponent) e.getComponent(HealthComponent.class);
        if (hp.getCurrentHealth() <= 0) {
            if (e.hasComponent(PlayerComponent.class)) {
                this.world.setGameOver();
            } else {
                world.notifyEvent(new RemoveEntityEvent(e));
            }
        }
    }
}
