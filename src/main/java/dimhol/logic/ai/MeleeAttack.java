package dimhol.logic.ai;

import dimhol.components.AiComponent;
import dimhol.components.MovementComponent;
import dimhol.entity.factories.AttackFactory;
import dimhol.entity.Entity;
import dimhol.events.AddEntityEvent;
import dimhol.events.Event;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class MeleeAttack extends AbstractAction {

    public MeleeAttack(int aggroRay, int waitTime) {
        super(aggroRay, waitTime);
    }

    public boolean canExecute(Entity entity) {
        aggroRay = Optional.of(AttackUtil.getMeleeRay(enemyPos.getPos(), enemyCentralPos, playerPos.getPos(), playerCentralPos));
        return super.canExecute(entity);
    }

    @Override
    public Optional<List<Event>> execute() {
        var movComp = (MovementComponent) enemy.getComponent(MovementComponent.class);
        var aiComp = (AiComponent) enemy.getComponent(AiComponent.class);
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
        var pos = AttackUtil.getAttackPos(dir, enemyCentralPos, enemyBody.getBodyShape(), AttackFactory.MELEE_WIDTH, AttackFactory.MELEE_HEIGHT);

        attacks.add(new AddEntityEvent(attackFactory.createMeleeAttack(pos, enemy)));
        return Optional.of(attacks);
    }
}
