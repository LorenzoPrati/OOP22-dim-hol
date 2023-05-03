package it.unibo.dimhol.logic.ai;

import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.events.Event;

import java.util.List;
import java.util.Optional;

public class FollowMovement extends AbstractAction {

    public FollowMovement(int aggroRay) {
        super(aggroRay);
    }

    @Override
    public Optional<List<Event>> execute() {
        var movComp = (MovementComponent) entity.getComponent(MovementComponent.class);
        movComp.setEnabled(true);

        var newDirection = AttackUtil.getPlayerDirection(playerCentralPos, enemyCentralPos);
        movComp.setDir(newDirection);

        return Optional.empty();
    }
}
