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

    public static final double BOSS_AREA_ATTACK_WIDTH = 3;
    public static final double BOSS_AREA_ATTACK_HEIGHT = 3;
    public static final double BOSS_MELEE_ATTACK_WIDTH = 2;
    public static final double BOSS_MELEE_ATTACK_HEIGHT = 2;
    public static final double ENEMY_MINS_WIDTH = 0.5;
    public static final double ENEMY_MINS_HEIGHT = 0.5;

    private final HealthEffectFactory healthEffectFactory = new HealthEffectsFactoryImpl();

    /**
     * Create a melee attack near the entity's body facing the direction
     * the entity is turned against.
     * @param pos is the attack position
     * @param entity is the assailant
     * @return attack entity
     */
    public Entity createEnemyMeleeAttack(final Vector2D pos, final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new BodyComponent(new RectBodyShape(ENEMY_MELEE_WIDTH, ENEMY_MELEE_HEIGHT), false))
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
    public Entity createEnemyDistanceAttack(final Vector2D pos, final Vector2D dir, final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new MovementComponent(dir, ENEMY_BULLET_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(ENEMY_BULLET_WIDTH, ENEMY_BULLET_HEIGHT), false))
                .add(new AnimationComponent(map.get("bullet"), DirectionUtil.getStringFromVec(dir)))
                .add(new AttackComponent(entity, List.of(healthEffectFactory.decreasePlayerCurrentHealthEffect(1))))
                .build();
    }

    public Entity createBossAreaAttack(final Vector2D pos, final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new BodyComponent(new RectBodyShape(BOSS_AREA_ATTACK_WIDTH, BOSS_AREA_ATTACK_HEIGHT), false))
                .add(new AttackComponent(entity, List.of(healthEffectFactory.decreasePlayerCurrentHealthEffect(2))))
                .build();
    }

    public Entity summonerEntityMinions(final Vector2D pos, final Entity entity) {
        return new EntityBuilder()
                .add(new PositionComponent(pos, 0))
                .add(new BodyComponent(new RectBodyShape(ENEMY_MINS_WIDTH, ENEMY_MINS_HEIGHT), false))
                .add(new AttackComponent(entity, List.of(healthEffectFactory.decreasePlayerCurrentHealthEffect(1))))
                .build();
    }
}
