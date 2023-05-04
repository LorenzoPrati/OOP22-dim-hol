package it.unibo.dimhol.logic.ai;

import it.unibo.dimhol.components.AiComponent;
import it.unibo.dimhol.components.MovementComponent;
import it.unibo.dimhol.entity.factories.AttackFactory;
import it.unibo.dimhol.entity.Entity;
import it.unibo.dimhol.events.AddEntityEvent;
import it.unibo.dimhol.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeleeAttack extends AbstractAction {

    public MeleeAttack(int aggroRay, int waitTime) {
        super(aggroRay, waitTime);
    }

    public boolean canExecute(Entity entity) {
        super.canExecute(entity);
        aggroRay = AttackUtil.getMeleeRay(entityPos.getPos(), enemyCentralPos, playerPos.getPos(), playerCentralPos);
        return MathUtilities.getDistance(playerCentralPos, enemyCentralPos) <= aggroRay;
    }

    @Override
    public Optional<List<Event>> execute() {
        var movComp = (MovementComponent) entity.getComponent(MovementComponent.class);
        var aiComp = (AiComponent) entity.getComponent(AiComponent.class);
        movComp.setEnabled(false);

        if (System.currentTimeMillis() - aiComp.getPrevMovTime() > waitTime) {
            aiComp.setPrevMovTime(System.currentTimeMillis());
            return meleeAttack();
        }
        return Optional.empty();
    }

    private Optional<List<Event>> meleeAttack() {
        List<Event> attacks = new ArrayList<>();
        var dir = AttackUtil.getPlayerDirection(playerCentralPos, enemyCentralPos);
        var pos = AttackUtil.getAttackPos(dir, enemyCentralPos, entityBody.getBodyShape(), AttackFactory.MELEE_WIDTH, AttackFactory.MELEE_HEIGHT);

        attacks.add(new AddEntityEvent(attackFactory.createMeleeAttack(pos, entity)));
        return Optional.of(attacks);
    }
}
