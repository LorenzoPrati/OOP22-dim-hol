package dimhol.logic.ai;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Implementation of enemy's behaviour routines.
 */
public class RoutineFactory {

    /**
     * Melee attack aggro.
     */
    public static final double MELEE_ATTACK_AGGRO = 1.5;
    /**
     * Distance attack aggro.
     */
    public static final double DISTANCE_ATTACK_AGGRO = 8;
    /**
     * Distance attack reload time.
     */
    public static final double DISTANCE_ATTACK_RELOAD_TIME = 2;
    /**
     * Random movement changes direction time.
     */
    public static final double CHANGE_DIRECTION_TIME = 1;
    /**
     * Melee reload time.
     */
    public static final double MELEE_RELOAD_TIME = 2;

    /**
     * Follow movement aggro.
     */
    public static final double FOLLOW_MOVEMENT_AGGRO = 5;

    /**
     * Boss melee reload time.
     */
    public static final double BOSS_MELEE_RELOAD_TIME = 2;
    /**
     * Boss distance attack reload time.
     */
    public static final double BOSS_DISTANCE_ATTACK_RELOAD_TIME = 3;
    /**
     * Boss Summon minions reload time.
     */
    private static final int SUMMONS_RELOAD_TIME = 15;
    /**
     * Boss Summon minions number.
     */
    public static final int BOSS_SUMMON_MINIONS_NUMBER = 3;
    /**
     * Boss damage for area aggro.
     */
    public static final double BOSS_AREA_ATTACK_AGGRO = 3;
    /**
     * Boss attack area reload time.
     */
    public static final double BOSS_AREA_ATTACK_RELOAD_TIME = 5;
    /**
     * Boss attack area damage.
     */
    private static final double BOSS_AREA_ATTACK_DAMAGE = 0.5;
    /**
     * Boss charge attack aggro ray.
     */
    private static final int CHARGE_ATTACK_AGGRO = 4;
    /**
     * Boss charge attack speed.
     */
    private static final double CHARGE_SPEED = 5;
    /**
     * Boss charge attack damage.
     */
    private static final int CHARGE_ATTACK_DAMAGE = 3;
    /**
     * Boss charge attack reload time.
     */
    private static final int CHARGE_ATTACK_RELOAD_TIME = 5;

    /**
     *
     * @return the behaviour of a shooter enemy.
     */
    public final List<Action> createShooterRoutine() {
        return Stream.of(
                new DistanceAttack(DISTANCE_ATTACK_AGGRO, DISTANCE_ATTACK_RELOAD_TIME),
                new RandomMovement(CHANGE_DIRECTION_TIME)
        ).sorted(Comparator.comparingDouble(AbstractAction::getAggroRay)).collect(Collectors.toList());
    }

    /**
     *
     * @return the behaviour of a zombie enemy.
     */
    public final List<Action> createZombieRoutine() {
        return Stream.of(
                new MeleeAttack(MELEE_ATTACK_AGGRO, MELEE_RELOAD_TIME),
                new FollowMovement(FOLLOW_MOVEMENT_AGGRO),
                new RandomMovement(CHANGE_DIRECTION_TIME)
        ).sorted(Comparator.comparingDouble(AbstractAction::getAggroRay)).collect(Collectors.toList());
    }

    /**
     * Create the behaviour routine for a boss enemy.
     * @return the list of actions in the boss routine
     */
    public List<Action> createBossRoutine() {
        return Stream.of(
                new MeleeAttack(MELEE_ATTACK_AGGRO, BOSS_MELEE_RELOAD_TIME),
                new DistanceAttack(DISTANCE_ATTACK_AGGRO, BOSS_DISTANCE_ATTACK_RELOAD_TIME),
                new AreaAttack(BOSS_AREA_ATTACK_AGGRO, BOSS_AREA_ATTACK_DAMAGE, BOSS_AREA_ATTACK_RELOAD_TIME),
                new ChargeAttack(CHARGE_SPEED, CHARGE_ATTACK_DAMAGE, CHARGE_ATTACK_AGGRO, CHARGE_ATTACK_RELOAD_TIME),
                new SummonMinions(SUMMONS_RELOAD_TIME, BOSS_SUMMON_MINIONS_NUMBER)
//                new DefensiveShield()
        ).sorted(Comparator.comparingDouble(AbstractAction::getAggroRay)).collect(Collectors.toList());
    }
}
