package dimhol.logic.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of enemy's behaviour routines.
 */
public class RoutineFactory {

    /**
     * Distance attack aggro.
     */
    public static final int DISTANCE_ATTACK_AGGRO = 8;
    /**
     * Distance attack reload time.
     */
    public static final int DISTANCE_ATTACK_RELOAD_TIME = 2;
    /**
     * Random movement changes direction time.
     */
    public static final int CHANGE_DIRECTION_TIME = 1;
    /**
     * Melee reload time.
     */
    public static final int MELEE_RELOAD_TIME = 2;

    /**
     * Follow movement aggro.
     */
    public static final int FOLLOW_MOVEMENT_AGGRO = 5;

    /**
     *
     * @return the behaviour of a shooter enemy.
     */
    public final List<AbstractAction> createShooterRoutine() {
        return new ArrayList<>(List.of(
                new DistanceAttack(DISTANCE_ATTACK_AGGRO, DISTANCE_ATTACK_RELOAD_TIME),
                new RandomMovement(CHANGE_DIRECTION_TIME)
        ));
    }

    /**
     *
     * @return the behaviour of a zombie enemy.
     */
    public final List<AbstractAction> createZombieRoutine() {
        return new ArrayList<>(List.of(
                new MeleeAttack(MELEE_RELOAD_TIME),
                new FollowMovement(FOLLOW_MOVEMENT_AGGRO),
                new RandomMovement(CHANGE_DIRECTION_TIME)
        ));
    }

}
