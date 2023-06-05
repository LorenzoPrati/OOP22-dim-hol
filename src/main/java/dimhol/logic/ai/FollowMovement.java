package dimhol.logic.ai;

import dimhol.events.WorldEvent;

import java.util.List;
import java.util.Optional;

/**
 * It's a strategy that changes the direction of the AI towards the player.
 */
public final class FollowMovement extends AbstractAction {

    /**
     * Follow Movement constructor.
     * @param followMovementAggro is the radius of the area in which the presence of an enemy
     *                            (the player) activates this strategy.
     */
    public FollowMovement(final double followMovementAggro) {
        setAggroRay(followMovementAggro);
    }

    @Override
    public Optional<List<WorldEvent>> execute() {
        getMovComp().setEnabled(true);
        var newDirection = BehviourUtil.getPlayerDirection(getPlayerCentralPos(), getEnemyCentralPos());
        getMovComp().setDir(newDirection);
        return Optional.empty();
    }
}
