package it.unibo.dimhol.logic.ai;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Implementation of enemy's behaviour routines.
 */
public class RoutineFactory {

    /**
     *
     * @return the behaviour of a shooter enemy.
     */
    public final List<Action> createShooterRoutine() {
        return new ArrayList<>(List.of(
                new Random_Movement(10, 1000)
                //new RandomMovementAction()
        ));
    }

    /**
     *
     * @return the behaviour of a zombie enemy.
     */
    public final List<Action> createZombieRoutine() {
        return new ArrayList<>(List.of(
                new Melee_Attack(),
                new Follow_Movement(5)
                //new MeleeAttackAction(),
                //new FollowingAction(),
                //new RandomMovementAction()
        ));
    }
}
