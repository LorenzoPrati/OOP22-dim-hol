package dimhol.logic.ai;

import dimhol.logic.ai.boss.BossMeleeAttackAction;
import dimhol.logic.ai.boss.BossSpeedBoostAction;

import java.util.List;

/**
 * Implementation of enemy's behavior routines.
 */
public final class RoutineFactory {

    /**
     * Melee attack aggro.
     */
    public static final double MELEE_ATTACK_AGGRO = 1.5;
    /**
     * Zombie change direction time.
     */
    public static final double ZOMBIE_CHANGE_DIRECTION_TIME = 1;
    /**
     * Distance attack aggro.
     */
    public static final double DISTANCE_ATTACK_AGGRO = 8;
    /**
     * Distance attack reload time.
     */
    public static final double DISTANCE_ATTACK_RELOAD_TIME = 2;
    /**
     * Random boss and minions movement changes direction time.
     */
    public static final double BOSS_CHANGE_DIRECTION_TIME = 0.5;
    /**
     * Random shop-keeper movement changes direction time.
     */
    private static final double SHOP_KEEPER_DIRECTION_TIME = 5;
    /**
     * Melee reload time.
     */
    public static final double MELEE_RELOAD_TIME = 2;

    /**
     * Follow movement aggro.
     */
    public static final double FOLLOW_MOVEMENT_AGGRO = 5;
    /**
     * Follow boss movement aggro.
     */
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
    public List<Action> createShooterRoutine() {
        return List.of(
                new DistanceAttack(DISTANCE_ATTACK_AGGRO, DISTANCE_ATTACK_RELOAD_TIME),
                new RandomMovement(ZOMBIE_CHANGE_DIRECTION_TIME)
        );
    }

    /**
     * Create a zombie behaviour.
     * @return the list of actions
     */
    public List<Action> createZombieRoutine() {
        return List.of(
                new MeleeAttack(MELEE_ATTACK_AGGRO, MELEE_RELOAD_TIME),
                new FollowMovement(FOLLOW_MOVEMENT_AGGRO),
                new RandomMovement(ZOMBIE_CHANGE_DIRECTION_TIME)
        );
    }

    /**
     * Create a boss behavior.
     * @return the list of actions
     */
    public List<Action> createBossRoutine() {
        return List.of(
                new BossSpeedBoostAction(),
                new BossMeleeAttackAction(BOSS_MELEE_ATTACK_AGGRO, BOSS_MELEE_ATTACK_RELOAD_TIME),
                new DistanceAttack(DISTANCE_ATTACK_AGGRO, BOSS_DISTANCE_ATTACK_RELOAD_TIME),
                new FollowMovement(BOSS_FOLLOW_MOVEMENT_AGGRO),
                new RandomMovement(BOSS_CHANGE_DIRECTION_TIME)
        );
    }

    /**
     * Create a minion behaviour.
     * @return the list of actions
     */
    public List<Action> createMinionRoutine() {
        return List.of(
                new MeleeAttack(MELEE_ATTACK_AGGRO, MINIONS_MELEE_RELOAD_TIME),
                new FollowMovement(FOLLOW_MOVEMENT_AGGRO),
                new RandomMovement(BOSS_CHANGE_DIRECTION_TIME)
        );
    }

    /**
     * Create shop keeper routine.
     * @return
     */
    public List<Action> createShopKeeperRoutine() {
        return List.of(
                new RandomMovement(SHOP_KEEPER_DIRECTION_TIME)
        );
    }
}
