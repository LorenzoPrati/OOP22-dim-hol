package dimhol.systems;

import dimhol.components.*;
import dimhol.core.WorldImpl;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;
import dimhol.logic.util.DirectionUtil;

public class CombatSystem extends AbstractSystem {
    /**
     * Constructs a system to operates on a given world and family of components.
     *
     * @param w     the world
     */
    public CombatSystem(WorldImpl w) {
        super(w, AttackComponent.class, CollisionComponent.class);
    }

    @Override
    public void process(Entity e, double dt) {
        AttackComponent attackComp = (AttackComponent) e.getComponent(AttackComponent.class);
        CollisionComponent collisionComp = (CollisionComponent) e.getComponent(CollisionComponent.class);

        if (attackComp.getEntity().hasComponent(AiComponent.class)) {
            for (var entity : collisionComp.getCollided()) {
                if (entity.hasComponent(PlayerComponent.class)) {
                    var healthComp = (HealthComponent) entity.getComponent(HealthComponent.class);
                    healthComp.setCurrentHealth(healthComp.getCurrentHealth()-attackComp.getDamage());
                }
            }
        } else if (attackComp.getEntity().hasComponent(PlayerComponent.class)) {
            for (var entity : collisionComp.getCollided()) {
                if (entity.hasComponent(AiComponent.class)) {
                    var healthComp = (HealthComponent) entity.getComponent(HealthComponent.class);
                    healthComp.setCurrentHealth(healthComp.getCurrentHealth()-attackComp.getDamage());
                }
            }
        }

    }
}
