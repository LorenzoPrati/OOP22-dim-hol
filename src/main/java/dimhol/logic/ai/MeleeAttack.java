package dimhol.logic.ai;

import dimhol.components.AiComponent;
import dimhol.components.MovementComponent;
import dimhol.entity.factories.AttackFactory;
import dimhol.events.AddEntityEvent;
import dimhol.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeleeAttack extends AbstractAction {

    public boolean canExecute() {
        super.aggroRay = AttackUtil.getMeleeRay(enemyPos.getPos(), enemyCentralPos, playerPos.getPos(), playerCentralPos);
        return super.canExecute();
    }

    @Override
    public Optional<List<Event>> execute() {
        System.out.println("melee...");
        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        var aiComp = (AiComponent) enemy.getComponent(AiComponent.class);
        movComp.setEnabled(false);
        aiComp.setPrevTime(aiComp.getCurrentTime());
        return meleeAttack();
    }

    private Optional<List<Event>> meleeAttack() {
        List<Event> attacks = new ArrayList<>();
        var dir = AttackUtil.getPlayerDirection(playerCentralPos, enemyCentralPos);
        var pos = AttackUtil.getAttackPos(dir, enemyCentralPos, enemyBody.getBodyShape(), AttackFactory.MELEE_WIDTH, AttackFactory.MELEE_HEIGHT);

        attacks.add(new AddEntityEvent(attackFactory.createMeleeAttack(pos, enemy)));
        return Optional.of(attacks);
    }
}
