package dimhol.logic.ai;

import dimhol.entity.factories.AttackFactory;
import dimhol.events.AddEntityEvent;
import dimhol.events.Event;

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
    public Optional<List<Event>> execute() {
        getMovComp().setEnabled(false);
        getAi().setPrevTime(getAi().getCurrentTime());
        if (getAi().getCurrentTime() - getAi().getPrevTime() >= getWaitingTime()) {
            getAi().setPrevTime(getAi().getCurrentTime());
            return distanceAttack();
        }
        return Optional.empty();
    }

    private Optional<List<Event>> distanceAttack() {
        List<Event> attacks = new ArrayList<>();
        var dir = AttackUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        var pos = AttackUtil.getAttackPos(dir, getEnemyCentralPos(), getEnemyBody().getBodyShape(),
                AttackFactory.MELEE_WIDTH, AttackFactory.MELEE_HEIGHT);

        attacks.add(new AddEntityEvent(getAttackFactory().createDistanceAttack(pos, dir, getEnemy())));
        return Optional.of(attacks);
    }
}
