package it.unibo.dimhol.logic.ai;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of enemy's behaviour routines.
 */
public class RoutineFactory {

    /**
     *
     * @return the behaviour of a shooter enemy.
     */
    public final List<AbstractAction> createShooterRoutine() {
        return new ArrayList<>(List.of(
                new DistanceAttack(8, 2000),
                new RandomMovement(100, 1000)
        )).stream().sorted(AbstractAction::getAggro).toList();
    }

    /**
     *
     * @return the behaviour of a zombie enemy.
     */
    public final List<AbstractAction> createZombieRoutine() {
        return new ArrayList<>(List.of(
                new MeleeAttack(1, 2000),
                new FollowMovement(5),
                new RandomMovement(100, 1000)
        )).stream().sorted(AbstractAction::getAggro).toList();
    }
}
