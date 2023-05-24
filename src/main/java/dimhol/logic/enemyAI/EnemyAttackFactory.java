package dimhol.logic.enemyAI;


import dimhol.components.*;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.entity.factories.AbstractAttackFactory;
import dimhol.logic.collision.RectBodyShape;
import dimhol.logic.util.DirectionUtil;

import java.util.function.Predicate;

/**
 * This class creates attack.
 */
public final class EnemyAttackFactory extends AbstractAttackFactory {

    /**
     * Bullet Width.
     */
    public static final double ENEMY_BULLET_WIDTH = 0.5;
    /**
     * Bullet Height.
     */
    public static final double ENEMY_BULLET_HEIGHT = 0.5;
    /**
     * Bullet speed.
     */
    private static final double ENEMY_BULLET_SPEED = 3;
    /**
     * Melee Width.
     */
    public static final double ENEMY_MELEE_WIDTH = 1;
    /**
     * Melee Height.
     */
    public static final double ENEMY_MELEE_HEIGHT = 1;
    /**
     * Enemy melee damage.
     */
    private static final int ENEMY_MELEE_DAMAGE = 1;
    /**
     * Enemy bullet damage.
     */
    private static final int ENEMY_BULLET_DAMAGE = 1;

    private Predicate<Entity> checkPlayer = entity -> entity.hasComponent(PlayerComponent.class);

    /**
     * Create a melee attack near the entity's body facing the direction
     * the entity is turned against.
     * @param entity is the assailant
     * @return attack entity
     */
    public Entity createMeleeAttack(final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(entity, ENEMY_MELEE_WIDTH, ENEMY_MELEE_HEIGHT), 0))
                .add(new BodyComponent(new RectBodyShape(ENEMY_MELEE_WIDTH, ENEMY_MELEE_HEIGHT), false))
                .add(new AttackComponent(ENEMY_MELEE_DAMAGE, checkPlayer))
                .add(new MeleeComponent())
                .add(new CollisionComponent())
                .add(new AnimationComponent(map.get("heart"), "idle"))
                .build();
    }

    /**
     * Create a bullet near the entity's body facing the direction
     * the entity is turned against, who will go in the aforementioned direction.
     * @param entity is the assailant
     * @return bullet entity
     */
    public Entity createDistanceAttack(final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(entity, ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT), 0))
                .add(new MovementComponent(getDirection(entity), ENEMY_BULLET_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT), false))
                .add(new CollisionComponent())
                .add(new AnimationComponent(map.get("bullet"), DirectionUtil.getStringFromVec(getDirection(entity))))
                .add(new AttackComponent(ENEMY_BULLET_DAMAGE, checkPlayer))
                .add(new BulletComponent())
                .build();
    }
}
