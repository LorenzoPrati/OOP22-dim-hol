package it.unibo.dimhol.systems;

import it.unibo.dimhol.World;
import it.unibo.dimhol.components.Component;
import it.unibo.dimhol.components.HealthComponent;
import it.unibo.dimhol.components.PlayerComponent;
import it.unibo.dimhol.entity.Entity;

public class StatSystem extends AbstractSystem {
    /**
     * Constructs a system to operates on a given world and family of components.
     *
     * @param w     the world
     */
    public StatSystem(World w) {
        super(w, HealthComponent.class, PlayerComponent.class);
    }

    @Override
    public void process(Entity e, double dt) {
        HealthComponent hP = (HealthComponent) e.getComponent(HealthComponent.class);
        System.out.println(hP.getCurrentHealth());
    }
}
