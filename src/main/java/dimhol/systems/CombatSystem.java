package dimhol.systems;

import dimhol.components.AttackComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.HealthComponent;
import dimhol.core.World;
import dimhol.entity.Entity;
import dimhol.events.RemoveEntityEvent;

/**
 *
 * This class checks if an entity with AttackComponent has collided with
 * an entity with HealthComponent and applies the damage.
 */
public final class CombatSystem extends AbstractSystem {

    /**
     * Construct a CombatSystem.
     */
    public CombatSystem() {
        super(AttackComponent.class, CollisionComponent.class);
    }

    @Override
    protected void process(final Entity entity, final double deltaTime, final World world) {
        final AttackComponent attackComp = (AttackComponent) entity.getComponent(AttackComponent.class);
        final CollisionComponent collisionComp = (CollisionComponent) entity.getComponent(CollisionComponent.class);

        for (final var collided : collisionComp.getCollided()) {
            if (attackComp.getPredicate().test(collided)) {
                final var healthComp = (HealthComponent) collided.getComponent(HealthComponent.class);
                healthComp.setCurrentHealth(healthComp.getCurrentHealth() - attackComp.getDamage());
                world.notifyEvent(new RemoveEntityEvent(entity));
            }
        }


    }
}
