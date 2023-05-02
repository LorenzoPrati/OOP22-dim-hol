package it.unibo.dimhol.systems;

import it.unibo.dimhol.core.WorldImpl;
import it.unibo.dimhol.logic.util.DirectionUtil;
import it.unibo.dimhol.components.*;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.RemoveEntityEvent;

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
        AttackComponent entityAttack = (AttackComponent) e.getComponent(AttackComponent.class);
        CollisionComponent entityCollision = (CollisionComponent) e.getComponent(CollisionComponent.class);

        for (var entity : entityCollision.getCollided()) {
            for (var effect : entityAttack.getEffets()) {
                if (effect.canUseOn(entity)){
                    effect.applyOn(entity);
                    world.notifyEvent(new RemoveEntityEvent(e));
                    if (entity.hasComponent(PlayerComponent.class)) {
                        var state = (AnimationComponent) entity.getComponent(AnimationComponent.class);
                        var mov = (MovementComponent) entity.getComponent((MovementComponent.class));
                        state.setState("hurt" + " " + DirectionUtil.getStringFromVec(mov.getDir()));
                        state.setCompleted(false);
                    }
                }
            }
        }

    }
}
