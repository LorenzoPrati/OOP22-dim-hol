package dimhol.entity.factories;


import dimhol.components.BodyComponent;
import dimhol.components.PositionComponent;
import dimhol.components.AttackComponent;
import dimhol.components.MovementComponent;
import dimhol.components.AnimationComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.collision.RectBodyShape;
import dimhol.logic.effects.HealthEffectFactory;
import dimhol.logic.effects.HealthEffectsFactoryImpl;
import dimhol.logic.util.DirectionUtil;
import org.locationtech.jts.math.Vector2D;

import java.util.List;

/**
 * This class creates attack.
 */
public final class EnemyAttackFactory extends AbstractFactory {

    /**
     * Bullet Width.
     */
    public static final double BULLET_WIDTH = 0.5;
    /**
     * Bullet Height.
     */
    public static final double BULLET_HEIGHT = 0.5;
    /**
     * Bullet speed.
     */
    private static final double BULLET_SPEED = 3;
    /**
     * Melee Width.
     */
    public static final double MELEE_WIDTH = 1;
    /**
     * Melee Height.
     */
    public static final double MELEE_HEIGHT = 1;

    private final HealthEffectFactory healthEffectFactory = new HealthEffectsFactoryImpl();

    /**
     * Create a melee attack near the entity's body facing the direction
     * the entity is turned against.
     * @param pos is the attack position
     * @param entity is the assailant
     * @return attack entity
     */
    public Entity createMeleeAttack(final Vector2D pos, final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new BodyComponent(new RectBodyShape(MELEE_WIDTH, MELEE_HEIGHT), false))
                .add(new AttackComponent(entity, List.of(healthEffectFactory.decreasePlayerCurrentHealthEffect(1))))
                .add(new AnimationComponent(map.get("heart"), "idle"))
                .build();
    }

    /**
     * Create a bullet near the entity's body facing the direction
     * the entity is turned against, who will go in the aforementioned direction.
     * @param pos is the attack position
     * @param dir is the bullet direction
     * @param entity is the assailant
     * @return bullet entity
     */
    public Entity createDistanceAttack(final Vector2D pos, final Vector2D dir, final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new MovementComponent(dir, BULLET_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(BULLET_WIDTH, BULLET_HEIGHT), false))
                .add(new AnimationComponent(map.get("bullet"), DirectionUtil.getStringFromVec(dir)))
                .add(new AttackComponent(entity, List.of(healthEffectFactory.decreasePlayerCurrentHealthEffect(1))))
                .build();
    }
}
