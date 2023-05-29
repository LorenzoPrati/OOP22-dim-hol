package dimhol.logic.enemyAI;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

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
     * Boss Summon minions number.
     */
    public static final int BOSS_SUMMON_MINS_NUMBER = 3;
    /**
     * Boss damage for area aggro.
     */
    public static final double BOSS_AREA_ATTACK_AGGRO = 3;
    private static final double BOSS_AREA_ATTACK_DAMAGE = 0.5;
    private static final double CHARGE_SPEED = 5;
    private static final int CHARGE_ATTACK_DAMAGE = 3;

    /**
     *
     * @return the behaviour of a shooter enemy.
     */
    public final List<Action> createShooterRoutine() {
        return new ArrayList<>(List.of(
                new DistanceAttack(DISTANCE_ATTACK_AGGRO, DISTANCE_ATTACK_RELOAD_TIME),
                new RandomMovement(CHANGE_DIRECTION_TIME)
        )).stream().sorted(Comparator.comparingDouble(AbstractAction::getAggroRay)).collect(Collectors.toList());

    }

    /**
     *
     * @return the behaviour of a zombie enemy.
     */
    public final List<Action> createZombieRoutine() {
        return new ArrayList<>(List.of(
                new MeleeAttack(MELEE_ATTACK_AGGRO, MELEE_RELOAD_TIME),
                new FollowMovement(FOLLOW_MOVEMENT_AGGRO),
                new RandomMovement(CHANGE_DIRECTION_TIME)
        )).stream().sorted(Comparator.comparingDouble(AbstractAction::getAggroRay)).collect(Collectors.toList());
    }

    public List<Action> createBossRoutine() {
        return new ArrayList<>(List.of(
                new MeleeAttack(MELEE_RELOAD_TIME),
//                new SummonMinions(),
                new AreaOfAttack(BOSS_AREA_ATTACK_AGGRO, BOSS_AREA_ATTACK_DAMAGE)
//                new ChargeAttack(CHARGE_SPEED, CHARGE_ATTACK_DAMAGE)
        ));
    }

    public List<Action> createMinsRuotine() {
        return new ArrayList<>(List.of(
                new MeleeAttack(MELEE_RELOAD_TIME),
                new FollowMovement(FOLLOW_MOVEMENT_AGGRO),
                new RandomMovement(CHANGE_DIRECTION_TIME)
        ));
    }
}
