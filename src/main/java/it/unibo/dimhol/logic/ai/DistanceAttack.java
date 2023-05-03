package it.unibo.dimhol.logic.ai;

import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.AttackFactory;
import it.unibo.dimhol.events.AddEntityEvent;
import it.unibo.dimhol.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DistanceAttack extends AbstractAction {

    public DistanceAttack(int aggroRay, int waitTime) {
        super(aggroRay, waitTime);
    }

    @Override
    public Optional<List<Event>> execute() {
        var movComp = (MovementComponent) entity.getComponent(MovementComponent.class);
        var aiComp = (AiComponent) entity.getComponent(AiComponent.class);
        movComp.setEnabled(false);

        if (System.currentTimeMillis() - aiComp.getPrevMovTime() > waitTime) {
            aiComp.setPrevMovTime(System.currentTimeMillis());
            return distanceAttack();
        }
        return Optional.empty();
    }

    private Optional<List<Event>> distanceAttack() {
        List<Event> attacks = new ArrayList<>();
        var dir = AttackUtil.getPlayerDirection(playerCentralPos, enemyCentralPos);
        var pos = AttackUtil.getAttackPos(dir, enemyCentralPos, entityBody.getBodyShape(), AttackFactory.MELEE_WIDTH, AttackFactory.MELEE_HEIGHT);

        attacks.add(new AddEntityEvent(attackFactory.createDistanceAttack(pos, dir, entity)));
        return Optional.of(attacks);
    }
}
