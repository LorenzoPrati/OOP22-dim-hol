package dimhol.entity.factories;


import dimhol.components.MeleeComponent;
import dimhol.components.AnimationComponent;
import dimhol.components.MovementComponent;
import dimhol.components.BulletComponent;
import dimhol.components.AttackComponent;
import dimhol.components.BodyComponent;
import dimhol.components.PlayerComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.logic.collision.RectBodyShape;
import dimhol.logic.util.DirectionUtil;

import java.util.function.Predicate;

/**
 * This class creates attack.
 */
public final class EnemyAttackFactory extends BaseAttackFactory {

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
    private static final int ENEMY_BULLET_SPEED = 3;
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

    /**
     * Area Attack Width.
     */
    public static final double BOSS_AREA_ATTACK_WIDTH = 3.5;
    /**
     * Area Attack Height.
     */
    public static final double BOSS_AREA_ATTACK_HEIGHT = 3.5;
    /**
     * Area Attack Damage.
     */
    private static final int BOSS_MELEE_ATTACK_DAMAGE = 5;

    private final Predicate<Entity> checkPlayer = entity -> entity.hasComponent(PlayerComponent.class);

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
                .add(new AnimationComponent(getAnimationsMap().get("enemyMeleeAttack"), "idle"))
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
                .add(new AnimationComponent(getAnimationsMap().get("bullet"),
                        DirectionUtil.getStringFromVec(getDirection(entity))))
                .add(new AttackComponent(ENEMY_BULLET_DAMAGE, checkPlayer))
                .add(new BulletComponent())
                .build();
    }

    /**
     * Create an area attack around the boss entity.
     * @param bossEntity the boss entity
     * @return the area attack entity
     */
    public Entity createBossMeleeAttack(final Entity bossEntity) {
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(bossEntity, BOSS_AREA_ATTACK_WIDTH, BOSS_AREA_ATTACK_HEIGHT), 0))
                .add(new BodyComponent(new RectBodyShape(BOSS_AREA_ATTACK_WIDTH, BOSS_AREA_ATTACK_HEIGHT), false))
                .add(new AttackComponent(BOSS_MELEE_ATTACK_DAMAGE, checkPlayer))
                .add(new MeleeComponent())
                .add(new AnimationComponent(getAnimationsMap().get("enemyMeleeAttack"), "idle"))
                .build();
    }
}
