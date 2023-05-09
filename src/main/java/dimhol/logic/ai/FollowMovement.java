package dimhol.logic.ai;

import dimhol.components.MovementComponent;
import dimhol.events.Event;

import java.util.List;
import java.util.Optional;

/**
 * It's a strategy that changes the direction of the AI towards the player.
 */
public final class FollowMovement extends AbstractAction {

    @Override
    public Optional<List<Event>> execute() {
        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        movComp.setEnabled(true);
        var newDirection = AttackUtil.getPlayerDirection(playerCentralPos, enemyCentralPos);
        movComp.setDir(newDirection);
        return Optional.empty();
    }
}
