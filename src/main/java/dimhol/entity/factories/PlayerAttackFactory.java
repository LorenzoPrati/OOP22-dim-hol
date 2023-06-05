package dimhol.entity.factories;

import dimhol.components.*;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.collision.CircleBodyShape;
import dimhol.logic.collision.RectBodyShape;
import dimhol.logic.util.DirectionUtil;

import java.util.function.Predicate;

public class PlayerAttackFactory extends AbstractAttackFactory {

    /**
     * Player melee width.
     */
    public static final double PLAYER_MELEE_WIDTH = 1;
    /**
     * Player melee height.
     */
    public static final double PLAYER_MELEE_HEIGHT = 1;
    /**
     * Player melee attack damage.
     */
    private static final int PLAYER_MELEE_DAMAGE = 1;
    /**
     * Player little bullet width.
     */
    public static final double PLAYER_LITTLE_BULLET_WIDTH = 0.5;
    /**
     * Player little bullet height.
     */
    public static final double PLAYER_LITTLE_BULLET_HEIGHT = 0.5;
    /**
     * Player little bullet speed.
     */
    public static final int PLAYER_LITTLE_BULLET_SPEED = 3;
    /**
     * Player little bullet damage.
     */
    private static final int PLAYER_LITTLE_BULLET_DAMAGE = 1;
    /**
     * Player big bullet width.
     */
    public static final double PLAYER_BIG_BULLET_WIDTH = 1;
    /**
     * Player big bullet height.
     */
    public static final double PLAYER_BIG_BULLET_HEIGHT = 1;
    /**
     * Player big bullet speed.
     */
    public static final int PLAYER_BIG_BULLET_SPEED = 3;
    /**
     * Player big bullet damage.
     */
    private static final int PLAYER_BIG_BULLET_DAMAGE = 2;
    /**
     * Fireball ray.
     */
    private static final double PLAYER_BIG_BULLET_RAY = 0.6;
    /**
     * Little bullet ray.
     */
    private static final double PLAYER_LITTLE_BULLET_RAY = 0.2;

    private final Predicate<Entity> checkEnemy = entity -> entity.hasComponent(AIComponent.class);

    public Entity createMeleeAttack(final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(entity, PLAYER_MELEE_WIDTH, PLAYER_MELEE_HEIGHT), 0))
                .add(new BodyComponent(new RectBodyShape(PLAYER_MELEE_WIDTH, PLAYER_MELEE_HEIGHT), false))
                .add(new AttackComponent(PLAYER_MELEE_DAMAGE, checkEnemy))
                .add(new MeleeComponent())
                .build();
    }

    public Entity createLittleBulletAttack(final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(entity,
                        PLAYER_LITTLE_BULLET_RAY * 2, PLAYER_LITTLE_BULLET_RAY * 2), 0))
                .add(new MovementComponent(getDirection(entity), PLAYER_LITTLE_BULLET_SPEED, true))
                .add(new BodyComponent(new CircleBodyShape(PLAYER_LITTLE_BULLET_RAY), false))
                .add(new AnimationComponent(map.get("bullet"), DirectionUtil.getStringFromVec(getDirection(entity))))
                .add(new AttackComponent(PLAYER_LITTLE_BULLET_DAMAGE, checkEnemy))
                .add(new BulletComponent())
                .build();
    }

    public Entity createBigBulletAttack(final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(entity,
                        PLAYER_BIG_BULLET_RAY * 2, PLAYER_BIG_BULLET_RAY * 2), 0))
                .add(new MovementComponent(getDirection(entity), PLAYER_BIG_BULLET_SPEED, true))
                .add(new BodyComponent(new CircleBodyShape(PLAYER_BIG_BULLET_RAY), false))
                .add(new AnimationComponent(map.get("fireball"), "idle"))
                .add(new AttackComponent(PLAYER_BIG_BULLET_DAMAGE, checkEnemy))
                .add(new BulletComponent())
                .build();
    }

}
