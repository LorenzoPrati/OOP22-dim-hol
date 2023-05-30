package dimhol.entity.factories;


import dimhol.components.AnimationComponent;
import dimhol.components.AttackComponent;
import dimhol.components.BodyComponent;
import dimhol.components.BulletComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.MeleeComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PlayerComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.gamelevels.bossactions.bossAI.AreaAttackComponent;
import dimhol.gamelevels.bossactions.bossAI.ChargeAttackComponent;
import dimhol.gamelevels.bossactions.bossAI.DefensiveShieldComponent;
import dimhol.gamelevels.bossactions.bossAI.MinionComponent;
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
    private static final int BOSS_AREA_ATTACK_DAMAGE = 5;
    /**
     * Charge Attack Width.
     */
    public static final double BOSS_CHARGE_ATTACK_WIDTH = 2;
    /**
     * Charge Attack Height.
     */
    public static final double BOSS_CHARGE_ATTACK_HEIGHT = 1.5;
    /**
     * Charge Attack Speed.
     */
    private static final int BOSS_CHARGE_ATTACK_SPEED = 5;
    /**
     * Charge Attack Damage.
     */
    private static final int BOSS_CHARGE_ATTACK_DAMAGE = 3;
    /**
     * Defensive Shield Duration.
     */
    private static final int BOSS_DEFENSIVE_SHIELD_DURATION = 10;
    /**
     * Minions height.
     */
    public static final double MINIONS_WIDTH = 3.5;
    /**
     * Minions width.
     */
    public static final double MINIONS_HEIGHT = 3.5;

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
                .add(new CollisionComponent())
                .add(new AnimationComponent(map.get("enemyMeleeAttack"), "idle"))
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

    /**
     * Create an area attack around the boss entity.
     * @param bossEntity the boss entity
     * @return the area attack entity
     */
    public Entity createAreaAttack(final Entity bossEntity) {
        System.out.printf("AreaAttack");
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(bossEntity, BOSS_AREA_ATTACK_WIDTH, BOSS_AREA_ATTACK_HEIGHT), 0))
                .add(new BodyComponent(new RectBodyShape(BOSS_AREA_ATTACK_WIDTH, BOSS_AREA_ATTACK_HEIGHT), false))
                .add(new AttackComponent(BOSS_AREA_ATTACK_DAMAGE, checkPlayer))
                .add(new AreaAttackComponent())
                .add(new CollisionComponent())
                .add(new AnimationComponent(map.get("boss"), "idle"))
                .build();
    }

    /**
     * Create a summoning minions action by the boss entity.
     * @param bossEntity the boss entity
     * @return the summoned minions entities
     */
    public Entity createSummoningMinions(final Entity bossEntity) {
        System.out.printf("Minions");
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(bossEntity, 5, 5), 0))
                .add(new BodyComponent(new RectBodyShape(MINIONS_WIDTH, MINIONS_HEIGHT), true))
                .add(new AttackComponent(BOSS_CHARGE_ATTACK_DAMAGE, checkPlayer))
                .add(new MinionComponent())
                .add(new CollisionComponent())
                .add(new AnimationComponent(map.get("boss"), "takeHit")) //TODO: add their sprites.
                .build();
    }

    /**
     * Create a charge attack by the boss entity.
     * @param bossEntity the boss entity
     * @return the charge attack entity
     */
    public Entity createChargeAttack(final Entity bossEntity) {
        System.out.printf("chargeAttack");
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(bossEntity, BOSS_CHARGE_ATTACK_WIDTH, BOSS_CHARGE_ATTACK_HEIGHT), 0))
                .add(new MovementComponent(getDirection(bossEntity), BOSS_CHARGE_ATTACK_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(BOSS_CHARGE_ATTACK_WIDTH, BOSS_CHARGE_ATTACK_WIDTH), false))
                .add(new AttackComponent(BOSS_CHARGE_ATTACK_DAMAGE, checkPlayer))
                .add(new ChargeAttackComponent())
                .add(new CollisionComponent())
                .add(new AnimationComponent(map.get("boss"), "attack"))
                //DirectionUtil.getStringFromVec(getDirection(bossEntity))))
                .build();
    }

    /**
     * Create a defensive shield by the boss entity.
     * @param bossEntity the boss entity
     * @return the defensive shield entity
     */
    public Entity createDefensiveShield(final Entity bossEntity) {
        System.out.printf("shield");
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(bossEntity, 0, 0), 0))
                .add(new BodyComponent(new RectBodyShape(2.5, 2.5), false))
                .add(new CollisionComponent())
                .add(new AnimationComponent(map.get("boss"), "idle"))
                .add(new DefensiveShieldComponent(BOSS_DEFENSIVE_SHIELD_DURATION))
                .build();
    }

    /**
     * Create a teleport action by the boss entity.
     * @param bossEntity the boss entity
     * @return the teleport destination position
     */
//    public PositionComponent createTeleport(final Entity bossEntity) {
//        double x = bossEntity.getPosition().getX()
//        double y = bossEntity.getPosition().getY()
//        return new PositionComponent(new Vector2D(x, y), 0);
//    }
}
