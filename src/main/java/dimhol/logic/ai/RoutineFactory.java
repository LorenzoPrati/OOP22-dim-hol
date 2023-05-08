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
        try {
            return new ArrayList<>(List.of(
                    new ActionBuilder<>(DistanceAttack.class)
                            .addAggroRay(DISTANCE_ATTACK_AGGRO)
                            .addWaitingTime(DISTANCE_ATTACK_RELOAD_TIME)
                            .build(),
                    new ActionBuilder<>(RandomMovement.class)
                            .addWaitingTime(CHANGE_DIRECTION_TIME)
                            .build()
            ));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     *
     * @return the behaviour of a zombie enemy.
     */
    public final List<AbstractAction> createZombieRoutine() {
        try {
            return new ArrayList<>(List.of(
                    new ActionBuilder<>(MeleeAttack.class)
                            .addWaitingTime(MELEE_RELOAD_TIME)
                            .build(),
                    new ActionBuilder<>(FollowMovement.class)
                            .addAggroRay(FOLLOW_MOVEMENT_AGGRO)
                            .build(),
                    new ActionBuilder<>(RandomMovement.class)
                            .addWaitingTime(CHANGE_DIRECTION_TIME)
                            .build()
            ));
        } catch (InstantiationException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

}
