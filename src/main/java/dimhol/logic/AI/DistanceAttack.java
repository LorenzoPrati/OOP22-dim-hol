package dimhol.logic.AI;

import dimhol.events.AddEntityEvent;
import dimhol.events.WorldEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * It is a strategy that creates bullets by aiming at the player.
 */
public final class DistanceAttack extends AbstractAction {

    /**
     * Distance Attack constructor.
     * @param distanceAttackAggro is the radius of the area in which the presence
     *                           of an enemy (the player) activates this strategy
     * @param distanceAttackReloadTime is the reload time of the ranged attack
     */
    public DistanceAttack(final double distanceAttackAggro, final double distanceAttackReloadTime) {
        setAggroRay(distanceAttackAggro);
        setWaitingTime(distanceAttackReloadTime);
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
        getMovComp().setEnabled(false);
        var direction = BehviourUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        getMovComp().setDir(direction);
        if (reloadTimePassed()) {
            return distanceAttack();
        }
        return Optional.empty();
    }

    private Optional<List<WorldEvent>> distanceAttack() {
        List<WorldEvent> attacks = new ArrayList<>();
        attacks.add(new AddEntityEvent(getAttackFactory().createDistanceAttack(getEnemy())));
        return Optional.of(attacks);
    }
}
