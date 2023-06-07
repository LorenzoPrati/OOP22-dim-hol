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
    public static final double CHANGE_DIRECTION_TIME = 0.5;
    /**
     * Melee reload time.
     */
    public static final double MELEE_RELOAD_TIME = 2;

    /**
     * Follow movement aggro.
     */
    public static final double FOLLOW_MOVEMENT_AGGRO = 5;
    public static final double BOSS_FOLLOW_MOVEMENT_AGGRO = 7;
    /**
     * Minions melee reload time.
     */
    public static final double MINIONS_MELEE_RELOAD_TIME = 1;
    /**
     * Boss distance attack reload time.
     */
    public static final double BOSS_DISTANCE_ATTACK_RELOAD_TIME = 3;
    /**
     * Boss damage for area aggro.
     */
    public static final double BOSS_MELEE_ATTACK_AGGRO = 5;
    /**
     * Boss attack area reload time.
     */
    public static final double BOSS_MELEE_ATTACK_RELOAD_TIME = 10;

    /**
     *
     * @return the behaviour of a shooter enemy.
     */
    public final List<Action> createShooterRoutine() {
        return List.of(
                new DistanceAttack(DISTANCE_ATTACK_AGGRO, DISTANCE_ATTACK_RELOAD_TIME),
                new RandomMovement()
        );
    }

    /**
     *
     * @return the behaviour of a zombie enemy.
     */
    public final List<Action> createZombieRoutine() {
        return List.of(
                new MeleeAttack(MELEE_ATTACK_AGGRO, MELEE_RELOAD_TIME),
                new FollowMovement(FOLLOW_MOVEMENT_AGGRO),
                new RandomMovement()
        );
    }

    /**
     * Create the behaviour routine for a boss enemy.
     * @return the list of actions in the boss routine
     */
    public List<Action> createBossRoutine() {
        return List.of(
                new BossSpeedBoostAction(),
                new BossMeleeAttackAction(BOSS_MELEE_ATTACK_AGGRO, BOSS_MELEE_ATTACK_RELOAD_TIME),
                new DistanceAttack(DISTANCE_ATTACK_AGGRO, BOSS_DISTANCE_ATTACK_RELOAD_TIME),
                new FollowMovement(BOSS_FOLLOW_MOVEMENT_AGGRO),
                new RandomMovement(CHANGE_DIRECTION_TIME)
        );
    }

    public List<Action> createMinionsRoutine() {
        return List.of(
                new MeleeAttack(MELEE_ATTACK_AGGRO, MINIONS_MELEE_RELOAD_TIME),
                new FollowMovement(FOLLOW_MOVEMENT_AGGRO),
                new RandomMovement(CHANGE_DIRECTION_TIME)
        );
    }
}
