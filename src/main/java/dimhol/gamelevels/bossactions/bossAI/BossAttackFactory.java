package dimhol.gamelevels.bossactions.bossAI;

import dimhol.components.AnimationComponent;
import dimhol.components.AttackComponent;
import dimhol.components.BodyComponent;
import dimhol.components.CollisionComponent;
import dimhol.components.MovementComponent;
import dimhol.components.PlayerComponent;
import dimhol.components.PositionComponent;
import dimhol.entity.Entity;
import dimhol.entity.EntityBuilder;
import dimhol.entity.factories.AbstractAttackFactory;
import dimhol.logic.collision.RectBodyShape;

import java.util.function.Predicate;

/**
 * This class creates boss attacks.
 */
public final class BossAttackFactory extends AbstractAttackFactory {

    /**
     * Area Attack Width.
     */
    public static final double BOSS_AREA_ATTACK_WIDTH = 3.5;
    /**
     * Area Attack Height.
     */
    public static final double BOSS_AREA_ATTACK_HEIGHT = 3.5;
    /**
     * Charge Attack Width.
     */
    public static final double BOSS_CHARGE_ATTACK_WIDTH = 2;
    /**
     * Charge Attack Height.
     */
    public static final double BOSS_CHARGE_ATTACK_HEIGHT = 1.5;
    /**
     * Minions height.
     */
    public static final double MINIONS_WIDTH = 3.5;
    /**
     * Minions width.
     */
    public static final double MINIONS_HEIGHT = 3.5;
    /**
     * Area Attack Damage.
     */
    private static final int BOSS_AREA_ATTACK_DAMAGE = 5;
    /**
     * Summoned Minion Count.
     */
    private static final int BOSS_MINION_COUNT = 3;
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
     * Teleport Distance.
     */
    private static final double BOSS_TELEPORT_DISTANCE = 5.0;
    private static final double SUMMON_ATTACK_WIDTH = 5;
    private static final double SUMMON_ATTACK_HEIGHT = 5;
    private static final double BOSS_DEFENSIVE_SHIELD_WIDTH = 6;
    private static final double BOSS_DEFENSIVE_SHIELD_HEIGHT = 6;

    private final Predicate<Entity> isPlayer = entity -> entity.hasComponent(PlayerComponent.class);

    /**
     * Create an area attack around the boss entity.
     *
     * @param bossEntity the boss entity
     * @return the area attack entity
     */
    public Entity createAreaAttack(final Entity bossEntity) {
        System.out.print("AreaAttack");
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(bossEntity, BOSS_AREA_ATTACK_WIDTH, BOSS_AREA_ATTACK_HEIGHT), 0))
                .add(new BodyComponent(new RectBodyShape(BOSS_AREA_ATTACK_WIDTH, BOSS_AREA_ATTACK_HEIGHT), false))
                .add(new AttackComponent(BOSS_AREA_ATTACK_DAMAGE, isPlayer))
                .add(new AreaAttackComponent())
                .add(new CollisionComponent())
                .add(new AnimationComponent(getAnimationsMap().get("boss"), "idle"))
                .build();
    }

    /**
     * Create a summoning minions action by the boss entity.
     *
     * @param bossEntity the boss entity
     * @return the summoned minions entities
     */
    public Entity createSummoningMinions(final Entity bossEntity) {
        System.out.print("Minions");
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(bossEntity, SUMMON_ATTACK_WIDTH, SUMMON_ATTACK_HEIGHT), 0))
                .add(new BodyComponent(new RectBodyShape(MINIONS_WIDTH, MINIONS_HEIGHT), false))
                .add(new AttackComponent(BOSS_CHARGE_ATTACK_DAMAGE, isPlayer))
                .add(new MinionComponent())
                .add(new CollisionComponent())
                .add(new AnimationComponent(getAnimationsMap().get("boss"), "idle"))
                .build();
    }

    /**
     * Create a charge attack by the boss entity.
     *
     * @param bossEntity the boss entity
     * @return the charge attack entity
     */
    public Entity createChargeAttack(final Entity bossEntity) {
        System.out.print("chargeAttack");
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(bossEntity, BOSS_CHARGE_ATTACK_WIDTH, BOSS_CHARGE_ATTACK_HEIGHT), 0))
                .add(new MovementComponent(getDirection(bossEntity), BOSS_CHARGE_ATTACK_SPEED, true))
                .add(new BodyComponent(new RectBodyShape(BOSS_CHARGE_ATTACK_WIDTH, BOSS_CHARGE_ATTACK_WIDTH), false))
                .add(new AttackComponent(BOSS_CHARGE_ATTACK_DAMAGE, isPlayer))
                .add(new ChargeAttackComponent())
                .add(new CollisionComponent())
                .add(new AnimationComponent(getAnimationsMap().get("boss"), "idle"))
                //DirectionUtil.getStringFromVec(getDirection(bossEntity))))
                .build();
    }

    /**
     * Create a defensive shield by the boss entity.
     *
     * @param bossEntity the boss entity
     * @return the defensive shield entity
     */
    public Entity createDefensiveShield(final Entity bossEntity) {
        System.out.print("shield");
        return new EntityBuilder()
                .add(new PositionComponent(getAttackPos(bossEntity, BOSS_DEFENSIVE_SHIELD_WIDTH,
                        BOSS_DEFENSIVE_SHIELD_HEIGHT), 0))
                .add(new BodyComponent(new RectBodyShape(BOSS_DEFENSIVE_SHIELD_WIDTH, BOSS_DEFENSIVE_SHIELD_HEIGHT), false))
                .add(new CollisionComponent())
                .add(new AnimationComponent(getAnimationsMap().get("boss"), "idle"))
                .add(new DefensiveShieldComponent(BOSS_DEFENSIVE_SHIELD_DURATION))
                .build();
    }

    /**
     * Create a teleport action by the boss entity.
     * @param bossEntity the boss entity
     * @return the teleport destination position
     */
//    public PositionComponent createTeleport(final Entity bossEntity) {
//        double x = bossEntity.getPosition().getX() + getRandomDouble(-BOSS_TELEPORT_DISTANCE, BOSS_TELEPORT_DISTANCE);
//        double y = bossEntity.getPosition().getY() + getRandomDouble(-BOSS_TELEPORT_DISTANCE, BOSS_TELEPORT_DISTANCE);
//        return new PositionComponent(new Vector2D(x, y), 0);
//    }

    // Other methods can be added here based on your requirements

    /**
     * Get a random double value within the specified range.
     * @param min the minimum value (inclusive)
     * @param max the maximum value (inclusive)
     * @return the random double value
     */
//    private double getRandomDouble(double min, double max) {
//        return min + Math.random() * (max - min + 1);
//    }
}
