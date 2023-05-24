package dimhol.systems;

import dimhol.components.*;
import dimhol.core.World;
import dimhol.entity.Entity;

public class CombatSystem extends AbstractSystem {

    public CombatSystem() {
        super(MeleeAttackComponent.class, CollisionComponent.class);
    }

    @Override
    public void process(final Entity entity, final double dt, final World world) {
        MeleeAttackComponent attackComp = (MeleeAttackComponent) entity.getComponent(MeleeAttackComponent.class);
        CollisionComponent collisionComp = (CollisionComponent) entity.getComponent(CollisionComponent.class);

        for (var collided : collisionComp.getCollided()) {
            if (attackComp.getPredicate().test(collided)) {
                var healthComp = (HealthComponent) collided.getComponent(HealthComponent.class);
                healthComp.setCurrentHealth(healthComp.getCurrentHealth() - attackComp.getDamage());
            }
        }


    }
}
