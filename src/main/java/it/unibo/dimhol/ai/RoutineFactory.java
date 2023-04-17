package it.unibo.dimhol.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of enemy's behaviour routines.
 */
public class RoutineFactory {

    /**
     * Shooter Aggro Ray.
     */
    public static final int SHOOTER_AGGRO_RAY = 200;

    /**
     * Zombie Aggro Ray.
     */
    public static final int ZOMBIE_AGGRO_RAY = 100;

    /**
     *
     * @return the behaviour of a shooter enemy.
     */
    public final List<Action> createShooterRoutine() {
        return new ArrayList<>(List.of(
                new RandomMovementAction(SHOOTER_AGGRO_RAY)
        ));
    }

    /**
     *
     * @return the behaviour of a zombie enemy.
     */
    public final List<Action> createZombieRoutine() {
        return new ArrayList<>(List.of(
                new RandomMovementAction(ZOMBIE_AGGRO_RAY)
        ));
    }
}
